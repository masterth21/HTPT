package vn.fs.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.fs.entity.Category;
import vn.fs.repository.CategoryDAO;
import vn.fs.service.CategoryBusiness;

@Service
public class CategoryBusinessImpl implements CategoryBusiness {

	@Autowired
	CategoryDAO categoryRepository;
	
	@Override
    public List<Category> getAll(int offset, int limit) {
        // Tạo đối tượng Pageable từ offset và limit
        Pageable pageable = PageRequest.of(offset, limit);
        
        // Sử dụng repository để lấy danh sách Category với phân trang
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        
        // Trả về danh sách Category từ Page
        return categoryPage.getContent();
    }
//	public List<Category> getAll() {
//		// TODO Auto-generated method stub
//		return categoryRepository.findAll();
//	}

	@Override
	public Category getById(Long id) {
		// TODO Auto-generated method stub
		if (!categoryRepository.existsById(id)) {
			return null;
		}
		return categoryRepository.findById(id).get();
	}

	@Override
	public Category post(Category category) {
		// TODO Auto-generated method stub
		if (!categoryRepository.existsById(category.getCategoryId())) {
			return categoryRepository.save(category);
		}
		return null;
	}

	@Override
	public Category put(Category category) {
		// TODO Auto-generated method stub
		Long categoryId = category.getCategoryId();
		if (categoryId == null || !categoryRepository.existsById(categoryId)) {
	        return null; // hoặc throw một ngoại lệ tùy thuộc vào logic của bạn
	    }
		return categoryRepository.save(category);
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		if (!categoryRepository.existsById(id)) {
	        return false;
	    }
		categoryRepository.deleteById(id);
	    return true;
	}

	
}
