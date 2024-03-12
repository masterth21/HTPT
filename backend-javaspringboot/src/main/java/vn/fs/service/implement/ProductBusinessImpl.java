package vn.fs.service.implement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entity.Category;
import vn.fs.entity.Product;
import vn.fs.repository.CategoryDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.service.ProductBusiness;

@Service
public class ProductBusinessImpl implements ProductBusiness {
	
	@Autowired
	ProductDAO productRepository;
	
	@Autowired
	CategoryDAO cRepo;

	@Override
	public List<Product> getAllActiveProducts() {
		return productRepository.findByStatusTrue();
	}

	@Override
	public List<Product> getBestSellers() {
		return productRepository.findByStatusTrueOrderBySoldDesc();
	}

	@Override
	public List<Product> getTop10BestSellers() {
		return productRepository.findTop10ByOrderBySoldDesc();
	}

	@Override
	public List<Product> getLatestProducts() {
		return productRepository.findByStatusTrueOrderByEnteredDateDesc();
	}

	@Override
	public List<Product> getTopRatedProducts() {
		return productRepository.findProductRated();
	}

	@Override
	public List<Product> getSuggestedProducts(Long categoryId, Long productId) {
		// You may implement your logic here to find suggested products based on categoryId and productId
	    return productRepository.findProductSuggest(categoryId, productId, categoryId, categoryId);
	}

	@Override
	public List<Product> getProductsByCategory(Long categoryId) {
		Optional<Category> categoryOptional = cRepo.findById(categoryId);
	    if (categoryOptional.isPresent()) {
	        Category category = categoryOptional.get();
	        return productRepository.findByCategory(category);
	    } else {
	        return Collections.emptyList(); // or you can throw an exception if needed
	    }
	}

	@Override
	public Product getProductById(Long id) {
		Optional<Product> productOptional = productRepository.findById(id);
	    return productOptional.orElse(null);
	}

	@Override
	public Product saveProduct(Product product) {
		if (productRepository.existsById(product.getProductId())) {
	        return null; // or throw an exception if desired
	    }
	    return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		// Kiểm tra xem sản phẩm tồn tại trong cơ sở dữ liệu không
	    if (!productRepository.existsById(product.getProductId())) {
	        return null; // Hoặc bạn có thể throw một exception nếu muốn
	    }
	    // Lưu sản phẩm được cập nhật vào cơ sở dữ liệu và trả về sản phẩm đã được cập nhật
	    return productRepository.save(product);
	}

	@Override
	public boolean deleteProductById(Long id) {
		// Kiểm tra xem sản phẩm tồn tại trong cơ sở dữ liệu không
	    if (!productRepository.existsById(id)) {
	        return false; // Hoặc bạn có thể throw một exception nếu muốn
	    }
	    // Lấy sản phẩm từ cơ sở dữ liệu và đặt trạng thái của nó thành false để đánh dấu xóa
	    Product product = productRepository.findById(id).get();
	    product.setStatus(false);
	    productRepository.save(product);
	    return true;
	}

	
}
