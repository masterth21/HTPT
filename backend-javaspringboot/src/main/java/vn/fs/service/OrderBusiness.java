package vn.fs.service;

import java.util.List;

import vn.fs.entity.Cart;
import vn.fs.entity.Order;

public interface OrderBusiness {

	List<Order> findAll();
	
	Order getOrderById(Long id);
	
	List<Order> getOrdersByUserEmail(String email);
	
	Order checkout(String email, Cart cart);
	
	void cancelOrder(Long orderId);
	
	void markOrderAsDelivered(Long orderId);
	
	void markOrderAsSuccess(Long orderId);
	
	void updateProduct(Order order);
}
