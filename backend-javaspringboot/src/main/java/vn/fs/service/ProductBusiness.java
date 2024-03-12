package vn.fs.service;

import java.util.List;

import vn.fs.entity.Product;

public interface ProductBusiness {

	List<Product> getAllActiveProducts();
	
	List<Product> getBestSellers();
	
	List<Product> getTop10BestSellers();
	
	List<Product> getLatestProducts();
	
	List<Product> getTopRatedProducts();
	
	List<Product> getSuggestedProducts(Long categoryId, Long productId);
	
	List<Product> getProductsByCategory(Long categoryId);
	
	 Product getProductById(Long id);
	 
	 Product saveProduct(Product product);
	 
	 Product updateProduct(Product product);
	 
	 boolean deleteProductById(Long id);
}
