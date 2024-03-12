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

import vn.fs.entity.Category;
import vn.fs.entity.Product;
import vn.fs.repository.CategoryDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.service.ProductBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
public class ProductService {

	@Autowired
	ProductDAO repo;

	@Autowired
	CategoryDAO cRepo;
	
	@Autowired
	ProductBusiness productService;

	@GetMapping
	public ResponseEntity<List<Product>> getAll() {
//		return ResponseEntity.ok(repo.findByStatusTrue());
		List<Product> activeProducts = productService.getAllActiveProducts();
		return ResponseEntity.ok(activeProducts);
	}

	@GetMapping("bestseller")
	public ResponseEntity<List<Product>> getBestSeller() {
//		return ResponseEntity.ok(repo.findByStatusTrueOrderBySoldDesc());
		List<Product> bestSellers = productService.getBestSellers();
	    return ResponseEntity.ok(bestSellers);
	}

	@GetMapping("bestseller-admin")
	public ResponseEntity<List<Product>> getBestSellerAdmin() {
//		return ResponseEntity.ok(repo.findTop10ByOrderBySoldDesc());
		List<Product> bestSellers = productService.getTop10BestSellers();
	    return ResponseEntity.ok(bestSellers);
	}

	@GetMapping("latest")
	public ResponseEntity<List<Product>> getLasted() {
//		return ResponseEntity.ok(repo.findByStatusTrueOrderByEnteredDateDesc());
		List<Product> latestProducts = productService.getLatestProducts();
		return ResponseEntity.ok(latestProducts);
	}

	@GetMapping("rated")
	public ResponseEntity<List<Product>> getRated() {
//		return ResponseEntity.ok(repo.findProductRated());
		List<Product> topRatedProducts = productService.getTopRatedProducts();
	    return ResponseEntity.ok(topRatedProducts);
	}

	@GetMapping("suggest/{categoryId}/{productId}")
	public ResponseEntity<List<Product>> suggest(@PathVariable("categoryId") Long categoryId,
			@PathVariable("productId") Long productId) {
//		return ResponseEntity.ok(repo.findProductSuggest(categoryId, productId, categoryId, categoryId));
		List<Product> suggestedProducts = productService.getSuggestedProducts(categoryId, productId);
	    return ResponseEntity.ok(suggestedProducts);
	}

	@GetMapping("category/{id}")
	public ResponseEntity<List<Product>> getByCategory(@PathVariable("id") Long id) {
//		if (!cRepo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Category c = cRepo.findById(id).get();
//		return ResponseEntity.ok(repo.findByCategory(c));
		List<Product> products = productService.getProductsByCategory(id);
	    if (products.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(products);
	    }
	}

	@GetMapping("{id}")
	public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
//		if (!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(repo.findById(id).get());
		Product product = productService.getProductById(id);
	    if (product == null) {
	        return ResponseEntity.notFound().build();
	    } else {
	        return ResponseEntity.ok(product);
	    }
	}

	@PostMapping
	public ResponseEntity<Product> post(@RequestBody Product product) {
//		if (repo.existsById(product.getProductId())) {
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.ok(repo.save(product));
		
		Product savedProduct = productService.saveProduct(product);
	    if (savedProduct == null) {
	        return ResponseEntity.badRequest().build();
	    } else {
	        return ResponseEntity.ok(savedProduct);
	    }
	}

	@PutMapping("{id}")
	public ResponseEntity<Product> put(@PathVariable("id") Long id, @RequestBody Product product) {
//		if (!id.equals(product.getProductId())) {
//			return ResponseEntity.badRequest().build();
//		}
//		if (!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(repo.save(product));
	    if (!id.equals(product.getProductId())) {
	        return ResponseEntity.badRequest().build();
	    }
	    Product updatedProduct = productService.updateProduct(product);
	    if (updatedProduct == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(updatedProduct);
		
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//		if (!repo.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		Product p = repo.findById(id).get();
//		p.setStatus(false);
//		repo.save(p);
//		return ResponseEntity.ok().build();

	    boolean isDeleted = productService.deleteProductById(id);
	    if (!isDeleted) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().build();
	}

}