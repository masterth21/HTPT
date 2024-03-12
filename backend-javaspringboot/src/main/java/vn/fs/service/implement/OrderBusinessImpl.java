package vn.fs.service.implement;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.fs.entity.Cart;
import vn.fs.entity.CartDetail;
import vn.fs.entity.Order;
import vn.fs.entity.OrderDetail;
import vn.fs.entity.Product;
import vn.fs.entity.User;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.OrderDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.OrderBusiness;
import vn.fs.utils.SendMailUtil;

@Service
public class OrderBusinessImpl implements OrderBusiness {

	@Autowired
	OrderDAO orderRepository;
	
	@Autowired
	UserDAO userRepository;
	
	@Autowired
	CartDAO cartRepository;
	
	@Autowired
	CartDetailDAO cartDetailRepository;
	
	@Autowired
	OrderDetailDAO orderDetailRepository;
	
	@Autowired
	ProductDAO productRepository;
	
	@Autowired
	SendMailUtil senMail;
	
	@Override
	public List<Order> findAll() {
        
		return orderRepository.findAllByOrderByOrdersIdDesc();
		
	}

	@Override
	public Order getOrderById(Long id) {
		Optional<Order> orderOptional = orderRepository.findById(id);
	    return orderOptional.orElse(null);
	}

	@Override
	public List<Order> getOrdersByUserEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        return orderRepository.findByUserOrderByOrdersIdDesc(user);
	    }
	    return Collections.emptyList();
	}

	@Override
	public Order checkout(String email, Cart cart) {
		Optional<User> userOptional = userRepository.findByEmail(email);
	    if (!userOptional.isPresent()) {
	        return null; // hoặc throw một ngoại lệ tùy thuộc vào logic của bạn
	    }
	    
	    Optional<Cart> cartOptional = cartRepository.findById(cart.getCartId());
	    if (!cartOptional.isPresent()) {
	        return null; // hoặc throw một ngoại lệ tùy thuộc vào logic của bạn
	    }

	    List<CartDetail> items = cartDetailRepository.findByCart(cart);
	    Double amount = 0.0;
	    for (CartDetail i : items) {
	        amount += i.getPrice();
	    }

	    Order order = new Order(0L, new Date(), amount, cart.getAddress(), cart.getPhone(), 0, userOptional.get());
	    order = orderRepository.save(order);

	    for (CartDetail i : items) {
	        OrderDetail orderDetail = new OrderDetail(0L, i.getQuantity(), i.getPrice(), i.getProduct(), order);
	        orderDetailRepository.save(orderDetail);
	    }

	    for (CartDetail i : items) {
	        cartDetailRepository.delete(i);
	    }

	    senMail.sendMailOrder(order);
	    
	    return order;
	}

	@Override
	public void cancelOrder(Long orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
	    if (orderOptional.isPresent()) {
	        Order order = orderOptional.get();
	        order.setStatus(3);
	        orderRepository.save(order);
	        senMail.sendMailOrderCancel(order);
	    }
		
	}

	@Override
	public void markOrderAsDelivered(Long orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
	    if (orderOptional.isPresent()) {
	        Order order = orderOptional.get();
	        order.setStatus(1);
	        orderRepository.save(order);
	        senMail.sendMailOrderDeliver(order);
	    }
		
	}

	@Override
	public void markOrderAsSuccess(Long orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
	    if (orderOptional.isPresent()) {
	        Order order = orderOptional.get();
	        order.setStatus(2);
	        orderRepository.save(order);
	        senMail.sendMailOrderSuccess(order);
	        updateProduct(order);
	    }
		
	}

	@Override
	public void updateProduct(Order order) {
		List<OrderDetail> listOrderDetail = orderDetailRepository.findByOrder(order);
		for (OrderDetail orderDetail : listOrderDetail) {
			Product product = productRepository.findById(orderDetail.getProduct().getProductId()).get();
			if (product != null) {
				product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
				product.setSold(product.getSold() + orderDetail.getQuantity());
				productRepository.save(product);
			}
		}
		
	}

	
}
