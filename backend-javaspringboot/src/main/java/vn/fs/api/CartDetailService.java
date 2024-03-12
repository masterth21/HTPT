package vn.fs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.CartDetail;
import vn.fs.entity.Product;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.service.CartDetailBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cartDetail")
public class CartDetailService {

	@Autowired
	CartDetailDAO cartDetailRepository;

	@Autowired
	CartDAO cartRepository;

	@Autowired
	ProductDAO productRepository;
	
	@Autowired 
	private CartDetailBusiness cartDetailService;

	@GetMapping("cart/{id}")
	public ResponseEntity<List<CartDetail>> getByCartId(@PathVariable("id") Long id) {
		List<CartDetail> list_cart = cartDetailService.getByCartId(id);
		if(list_cart != null) {
			return ResponseEntity.ok(list_cart);
		}
		return ResponseEntity.notFound().build();
//		if (!cartRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(cartDetailRepository.findByCart(cartRepository.findById(id).get()));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<CartDetail> getOne(@PathVariable("id") Long id) {
//		if (!cartDetailRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(cartDetailRepository.findById(id).get());
		CartDetail cartDetail = cartDetailService.getOne(id);
		if(cartDetail != null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cartDetail);
	}

	@PostMapping()
	public ResponseEntity<CartDetail> post(@RequestBody CartDetail detail) {
//		if (!cartRepository.existsById(detail.getCart().getCartId())) {
//			return ResponseEntity.notFound().build();
//		}
//		boolean check = false;
//		List<Product> listP = productRepository.findByStatusTrue();
//		Product product = productRepository.findByProductIdAndStatusTrue(detail.getProduct().getProductId());
//		for (Product p : listP) {
//			if (p.getProductId() == product.getProductId()) {
//				check = true;
//			}
//		}
//		;
//		if (!check) {
//			return ResponseEntity.notFound().build();
//		}
//		List<CartDetail> listD = cartDetailRepository
//				.findByCart(cartRepository.findById(detail.getCart().getCartId()).get());
//		for (CartDetail item : listD) {
//			if (item.getProduct().getProductId() == detail.getProduct().getProductId()) {
//				item.setQuantity(item.getQuantity() + 1);
//				item.setPrice(item.getPrice() + detail.getPrice());
//				return ResponseEntity.ok(cartDetailRepository.save(item));
//			}
//		}
//		return ResponseEntity.ok(cartDetailRepository.save(detail));
		
		CartDetail cartDetail = cartDetailService.post(detail);
		if (cartDetail == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cartDetail);
	}

	@PutMapping()
	public ResponseEntity<CartDetail> put(@RequestBody CartDetail detail) {
//		if (!cartRepository.existsById(detail.getCart().getCartId())) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(cartDetailRepository.save(detail));
		CartDetail cartDetail = cartDetailService.put(detail);
		if(cartDetail == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cartDetail);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//		if (!cartDetailRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		cartDetailRepository.deleteById(id);
//		return ResponseEntity.ok().build();
		
	    CartDetail cartDetail = cartDetailService.deleteById(id);
	    if (cartDetail == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().build();
	}

}
