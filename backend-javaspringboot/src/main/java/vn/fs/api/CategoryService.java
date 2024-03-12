package vn.fs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Category;
import vn.fs.repository.CategoryDAO;
import vn.fs.service.CategoryBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/categories")
public class CategoryService {

	@Autowired
	CategoryDAO repo;
	
	@Autowired
	CategoryBusiness categoryService;

	@GetMapping
	public ResponseEntity<List<Category>> getAll(@RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {
		List<Category> category_list = categoryService.getAll(offset, limit);
		return ResponseEntity.ok(category_list);
	}

	@GetMapping("{id}")
	public ResponseEntity<Category> getById(@PathVariable("id") Long id) {
//		if (!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(repo.findById(id).get());
		Category category = categoryService.getById(id);
		if(category == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(category);
	}

	@PostMapping
	public ResponseEntity<Category> post(@RequestBody Category category) {
//		if (repo.existsById(category.getCategoryId())) {
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.ok(repo.save(category));
		Category cate = categoryService.post(category);
		if (cate == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(cate);
	}

	@PutMapping("{id}")
	public ResponseEntity<Category> put(@RequestBody Category category, @PathVariable("id") Long id) {
		if (!id.equals(category.getCategoryId())) {
			return ResponseEntity.badRequest().build();
		}
		Category updatedCategory = categoryService.put(category);
		if (updatedCategory == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//		if (!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		repo.deleteById(id);
//		return ResponseEntity.ok().build();
		
		if (!categoryService.deleteById(id)) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().build();
	}

}
