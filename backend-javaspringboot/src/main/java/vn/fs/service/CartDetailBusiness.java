package vn.fs.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import vn.fs.entity.CartDetail;

public interface CartDetailBusiness {

	List<CartDetail> getByCartId(Long id);
	
	CartDetail getOne(Long id);
	
	CartDetail post(CartDetail detail);
	
	CartDetail put(CartDetail detail);
	
	CartDetail deleteById(Long id);
}
