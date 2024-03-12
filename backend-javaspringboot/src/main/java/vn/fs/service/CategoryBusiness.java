package vn.fs.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import vn.fs.entity.Category;

public interface CategoryBusiness {

	List<Category> getAll(int offset, int limit);
	
	Category getById(Long id);
	
	Category post(Category category);
	
	Category put(Category category);
	
	boolean deleteById(Long id);
	
}
