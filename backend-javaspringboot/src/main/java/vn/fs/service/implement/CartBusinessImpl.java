package vn.fs.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.fs.entity.Cart;
import vn.fs.repository.CartDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.CartBusiness;

@Service
public class CartBusinessImpl implements CartBusiness {

	@Autowired
	UserDAO userRepository;
	
	@Autowired
	CartDAO cartRepository;
	
	@Override
	public Cart getCartUser(String email) {
		// TODO Auto-generated method stub
		if (!userRepository.existsByEmail(email)) {
			return null;
		}
		return cartRepository.findByUser(userRepository.findByEmail(email).get());
	}

	@Override
	public Cart putCartUser(String email, Cart cart) {
		// TODO Auto-generated method stub
		if (!userRepository.existsByEmail(email)) {
			return null;
		}
		return cartRepository.save(cart);
	}

	
}
