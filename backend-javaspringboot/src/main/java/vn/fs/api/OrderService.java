package vn.fs.api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Cart;
import vn.fs.entity.CartDetail;
import vn.fs.entity.Order;
import vn.fs.entity.OrderDetail;
import vn.fs.entity.Product;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.OrderDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.OrderBusiness;
import vn.fs.utils.SendMailUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orders")
public class OrderService {

	@Autowired
	OrderDAO orderRepository;

	@Autowired
	OrderDetailDAO orderDetailRepository;

	@Autowired
	UserDAO userRepository;

	@Autowired
	CartDAO cartRepository;

	@Autowired
	CartDetailDAO cartDetailRepository;

	@Autowired
	ProductDAO productRepository;

	@Autowired
	SendMailUtil senMail;
	
	@Autowired
	OrderBusiness orderService;

	@GetMapping
	public ResponseEntity<List<Order>> findAll() {
//		return ResponseEntity.ok(orderRepository.findAllByOrderByOrdersIdDesc());
		return ResponseEntity.ok(orderService.findAll());
	}
	
	

	@GetMapping("{id}")
	public ResponseEntity<Order> getById(@PathVariable("id") Long id) {
//		if (!orderRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(orderRepository.findById(id).get());
		Order order = orderService.getOrderById(id);
	    if (order != null) {
	        return ResponseEntity.ok(order);
	    }
	    return ResponseEntity.notFound().build();
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<List<Order>> getByUser(@PathVariable("email") String email) {
//		if (!userRepository.existsByEmail(email)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity
//				.ok(orderRepository.findByUserOrderByOrdersIdDesc(userRepository.findByEmail(email).get()));
		
		List<Order> orders = orderService.getOrdersByUserEmail(email);
	    if (!orders.isEmpty()) {
	        return ResponseEntity.ok(orders);
	    }
	    return ResponseEntity.notFound().build();
	}

	@PostMapping("/{email}")
	public ResponseEntity<Order> checkout(@PathVariable("email") String email, @RequestBody Cart cart) {
//		if (!userRepository.existsByEmail(email)) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!cartRepository.existsById(cart.getCartId())) {
//			return ResponseEntity.notFound().build();
//		}
//		List<CartDetail> items = cartDetailRepository.findByCart(cart);
//		Double amount = 0.0;
//		for (CartDetail i : items) {
//			amount += i.getPrice();
//		}
//		Order order = orderRepository.save(new Order(0L, new Date(), amount, cart.getAddress(), cart.getPhone(), 0,
//				userRepository.findByEmail(email).get()));
//		for (CartDetail i : items) {
//			OrderDetail orderDetail = new OrderDetail(0L, i.getQuantity(), i.getPrice(), i.getProduct(), order);
//			orderDetailRepository.save(orderDetail);
//		}
////		cartDetailRepository.deleteByCart(cart);
//		for (CartDetail i : items) {
//			cartDetailRepository.delete(i);
//		}
//		senMail.sendMailOrder(order);
//		return ResponseEntity.ok(order);
		
		Order order = orderService.checkout(email, cart);
	    if (order != null) {
	        return ResponseEntity.ok(order);
	    }
	    return ResponseEntity.notFound().build();
	    
	}

	@GetMapping("cancel/{orderId}")
	public ResponseEntity<Void> cancel(@PathVariable("orderId") Long id) {
//		if (!orderRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Order order = orderRepository.findById(id).get();
//		order.setStatus(3);
//		orderRepository.save(order);
//		senMail.sendMailOrderCancel(order);
//		return ResponseEntity.ok().build();
		orderService.cancelOrder(id);
	    return ResponseEntity.ok().build();
	}

	@GetMapping("deliver/{orderId}")
	public ResponseEntity<Void> deliver(@PathVariable("orderId") Long id) {
//		if (!orderRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Order order = orderRepository.findById(id).get();
//		order.setStatus(1);
//		orderRepository.save(order);
//		senMail.sendMailOrderDeliver(order);
//		return ResponseEntity.ok().build();
		orderService.markOrderAsDelivered(id);
	    return ResponseEntity.ok().build();
	}

	@GetMapping("success/{orderId}")
	public ResponseEntity<Void> success(@PathVariable("orderId") Long id) {
//		if (!orderRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Order order = orderRepository.findById(id).get();
//		order.setStatus(2);
//		orderRepository.save(order);
//		senMail.sendMailOrderSuccess(order);
//		updateProduct(order);
//		return ResponseEntity.ok().build();
		orderService.markOrderAsSuccess(id);
	    return ResponseEntity.ok().build();
	}

//	public void updateProduct(Order order) {
//		List<OrderDetail> listOrderDetail = orderDetailRepository.findByOrder(order);
//		for (OrderDetail orderDetail : listOrderDetail) {
//			Product product = productRepository.findById(orderDetail.getProduct().getProductId()).get();
//			if (product != null) {
//				product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
//				product.setSold(product.getSold() + orderDetail.getQuantity());
//				productRepository.save(product);
//			}
//		}
//	}

}
