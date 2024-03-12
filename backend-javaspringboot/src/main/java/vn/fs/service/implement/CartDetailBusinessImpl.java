package vn.fs.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.fs.entity.Cart;
import vn.fs.entity.CartDetail;
import vn.fs.entity.Product;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.service.CartDetailBusiness;



@Service
public class CartDetailBusinessImpl implements CartDetailBusiness {
	
	@Autowired
	CartDAO cartRepository;
	
	@Autowired
	CartDetailDAO cartDetailRepository;

	@Autowired
	ProductDAO productRepository;
	
	@Override
	public List<CartDetail> getByCartId(Long id) {
		// TODO Auto-generated method stub
		if (!cartRepository.existsById(id)) {
			return null;
		}
		
		return cartDetailRepository.findByCart(cartRepository.findById(id).get());
	}

	@Override
	public CartDetail getOne(Long id) {
		// TODO Auto-generated method stub
		if (!cartDetailRepository.existsById(id)) {
			return null;
		}
		return cartDetailRepository.findById(id).get();
	}

	@Override
	public CartDetail post(CartDetail detail) {
		// TODO Auto-generated method stub
		Cart cart = detail.getCart();
		if (!cartRepository.existsById(cart.getCartId())) {
			return null;
		}
		boolean check = false;
		List<Product> listP = productRepository.findByStatusTrue();
		Product product = productRepository.findByProductIdAndStatusTrue(detail.getProduct().getProductId());
		for (Product p : listP) {
			if (p.getProductId() == product.getProductId()) {
				check = true;
			}
		};
		
		if (!check) {
			return null;
		}
		List<CartDetail> listD = cartDetailRepository
				.findByCart(cartRepository.findById(detail.getCart().getCartId()).get());
		for (CartDetail item : listD) {
			if (item.getProduct().getProductId() == detail.getProduct().getProductId()) {
				item.setQuantity(item.getQuantity() + 1);
				item.setPrice(item.getPrice() + detail.getPrice());
				return cartDetailRepository.save(item);
			}
		}
		return cartDetailRepository.save(detail);
		
	}

	@Override
	public CartDetail put(CartDetail detail) {
		// TODO Auto-generated method stub
		if (!cartRepository.existsById(detail.getCart().getCartId())) {
			return null;
		}
		return cartDetailRepository.save(detail);
	}

	@Override
	public CartDetail deleteById(Long id) {
		Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
	    if (!cartDetailOptional.isPresent()) {
	        return null;
	    }
	    CartDetail cartDetail = cartDetailOptional.get();
	    cartDetailRepository.deleteById(id);
	    return cartDetail;
	}


	
}
