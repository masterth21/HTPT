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
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Rate;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.RateDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.RateBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/rates")
public class RateService {

	@Autowired
	RateDAO rateRepository;

	@Autowired
	UserDAO userRepository;

	@Autowired
	OrderDetailDAO orderDetailRepository;

	@Autowired
	ProductDAO productRepository;

	
	@Autowired
	RateBusiness rateService;
	
	@GetMapping
	public ResponseEntity<List<Rate>> findAll() {
		return ResponseEntity.ok(rateRepository.findAllByOrderByIdDesc());
	}

	@GetMapping("{orderDetailId}")
	public ResponseEntity<Rate> findById(@PathVariable Long orderDetailId) {
//		if (!orderDetailRepository.existsById(orderDetailId)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(rateRepository.findByOrderDetail(orderDetailRepository.findById(orderDetailId).get()));
	    Rate rate = rateService.findRateByOrderDetailId(orderDetailId);
	    if (rate == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(rate);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<List<Rate>> findByProduct(@PathVariable("id") Long id) {
//		if (!productRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(rateRepository.findByProductOrderByIdDesc(productRepository.findById(id).get()));
		List<Rate> rates = rateService.findRatesByProductId(id);
	    // Kiểm tra xem có đánh giá nào không
	    if (rates.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    // Trả về ResponseEntity 200 OK với danh sách các đánh giá
	    return ResponseEntity.ok(rates);
	}

	@PostMapping
	public ResponseEntity<Rate> post(@RequestBody Rate rate) {
//		if (!userRepository.existsById(rate.getUser().getUserId())) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!productRepository.existsById(rate.getProduct().getProductId())) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!orderDetailRepository.existsById(rate.getOrderDetail().getOrderDetailId())) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(rateRepository.save(rate));
		 Rate savedRate = rateService.saveRate(rate);
		 // Kiểm tra xem đánh giá có được lưu thành công không
		 if (savedRate == null) {
		      return ResponseEntity.notFound().build();
		 }
		    // Trả về ResponseEntity 200 OK với đánh giá đã được lưu
		 return ResponseEntity.ok(savedRate);
	}

	@PutMapping
	public ResponseEntity<Rate> put(@RequestBody Rate rate) {
//		if (!rateRepository.existsById(rate.getId())) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(rateRepository.save(rate));
		// Gọi service method để cập nhật đánh giá
	    Rate updatedRate = rateService.updateRate(rate);
	    // Kiểm tra xem đánh giá đã được cập nhật thành công không
	    if (updatedRate == null) {
	        return ResponseEntity.notFound().build();
	    }
	    // Trả về ResponseEntity 200 OK với đánh giá đã được cập nhật
	    return ResponseEntity.ok(updatedRate);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//		if (!rateRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		rateRepository.deleteById(id);
//		return ResponseEntity.ok().build();
		// Gọi service method để xóa đánh giá
	    rateService.deleteRateById(id);
	    // Trả về ResponseEntity 200 OK nếu xóa thành công
	    return ResponseEntity.ok().build();
	}

}
