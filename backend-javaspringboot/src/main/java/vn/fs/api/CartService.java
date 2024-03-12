package vn.fs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Cart;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.CartBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cart")
public class CartService {

	@Autowired
	CartDAO cartRepository;

	@Autowired
	CartDetailDAO cartDetailRepository;

	@Autowired
	UserDAO userRepository;
	
	@Autowired
	private CartBusiness cartService;

	@GetMapping("/user/{email}")
	public ResponseEntity<Cart> getCartUser(@PathVariable("email") String email) {
//		if (!userRepository.existsByEmail(email)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(cartRepository.findByUser(userRepository.findByEmail(email).get()));
		Cart cart = cartService.getCartUser(email);
		if(cart != null) {
			return ResponseEntity.ok(cart);
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/user/{email}")
	public ResponseEntity<Cart> putCartUser(@PathVariable("email") String email, @RequestBody Cart cart) {
		Cart cart2 = cartService.putCartUser(email, cart);
		if(cart2 != null) {
			return ResponseEntity.ok(cart2);
		}
//		if (!userRepository.existsByEmail(email)) {
//			return ResponseEntity.notFound().build();
//		}
		return ResponseEntity.notFound().build();
	}

}
