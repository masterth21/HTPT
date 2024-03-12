package vn.fs.service.implement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entity.Order;
import vn.fs.entity.OrderDetail;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.OrderDAO;
import vn.fs.service.OderDetailBusiness;

@Service
public class OderDetailBusinessImpl implements OderDetailBusiness {

	@Autowired
	OrderDAO orderRepository;
	
	@Autowired
	OrderDetailDAO orderDetailRepository;
	
	@Override
	public List<OrderDetail> getByOrderId(Long orderId) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
	    if (orderOptional.isPresent()) {
	        return orderDetailRepository.findByOrder(orderOptional.get());
	    }
	    return Collections.emptyList();
	}

}
