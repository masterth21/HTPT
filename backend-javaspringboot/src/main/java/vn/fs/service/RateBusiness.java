package vn.fs.service;

import java.util.List;

import vn.fs.entity.Rate;

public interface RateBusiness {

	Rate findRateByOrderDetailId(Long orderDetailId);
	
	 List<Rate> findRatesByProductId(Long productId);
	 
	 Rate saveRate(Rate rate);
	 
	 Rate updateRate(Rate rate);
	 
	 void deleteRateById(Long id);
}
