package vn.fs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.OrderDetail;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.OrderDAO;
import vn.fs.service.OderDetailBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/orderDetail")
public class OderDetailService {

	@Autowired
	OrderDetailDAO orderDetailRepository;

	@Autowired
	OrderDAO orderRepository;
	
	@Autowired
	OderDetailBusiness oderDetailService;

	@GetMapping("/order/{id}")
	public ResponseEntity<List<OrderDetail>> getByOrder(@PathVariable("id") Long id) {
//		if (!orderRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(orderDetailRepository.findByOrder(orderRepository.findById(id).get()));
		List<OrderDetail> orderDetails = oderDetailService.getByOrderId(id);
	    if (!orderDetails.isEmpty()) {
	        return ResponseEntity.ok(orderDetails);
	    }
	    return ResponseEntity.notFound().build();
	}

}
