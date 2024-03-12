package vn.fs.service.implement;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entity.Rate;
import vn.fs.repository.OrderDetailDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.RateDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.RateBusiness;

@Service
public class RateBusinessImpl implements RateBusiness {

	@Autowired
	OrderDetailDAO orderDetailRepository;
	
	@Autowired
	RateDAO rateRepository;
	
	@Autowired
	ProductDAO productRepository;
	
	@Autowired
	UserDAO userRepository;
	
	@Override
	public Rate findRateByOrderDetailId(Long orderDetailId) {
		// Kiểm tra xem order detail có tồn tại không
        if (!orderDetailRepository.existsById(orderDetailId)) {
            return null;
        }
        // Tìm kiếm đánh giá dựa trên order detail ID
        return rateRepository.findByOrderDetail(orderDetailRepository.findById(orderDetailId).get());
	}
	@Override
	public List<Rate> findRatesByProductId(Long productId) {
		// Kiểm tra xem sản phẩm có tồn tại không
        if (!productRepository.existsById(productId)) {
            return Collections.emptyList();
        }
        // Tìm kiếm các đánh giá dựa trên ID của sản phẩm
        return rateRepository.findByProductOrderByIdDesc(productRepository.findById(productId).get());
	}
	@Override
	public Rate saveRate(Rate rate) {
		// Kiểm tra xem người dùng, sản phẩm và chi tiết đơn hàng có tồn tại không
        if (!userRepository.existsById(rate.getUser().getUserId()) ||
            !productRepository.existsById(rate.getProduct().getProductId()) ||
            !orderDetailRepository.existsById(rate.getOrderDetail().getOrderDetailId())) {
            return null;
        }
        // Lưu đánh giá và trả về kết quả
        return rateRepository.save(rate);
	}
	@Override
	public Rate updateRate(Rate rate) {
		 // Kiểm tra xem đánh giá có tồn tại không
        if (!rateRepository.existsById(rate.getId())) {
            return null;
        }
        // Lưu và cập nhật đánh giá, sau đó trả về kết quả
        return rateRepository.save(rate);
	}
	@Override
	public void deleteRateById(Long id) {
		 // Kiểm tra xem đánh giá có tồn tại không
        if (rateRepository.existsById(id)) {
            rateRepository.deleteById(id);
        }
		
	}

	
}
