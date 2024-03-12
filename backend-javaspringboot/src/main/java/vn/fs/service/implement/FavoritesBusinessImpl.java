package vn.fs.service.implement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.entity.Favorite;
import vn.fs.entity.Product;
import vn.fs.entity.User;
import vn.fs.repository.FavoriteDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.FavoritesBusiness;

@Service
public class FavoritesBusinessImpl implements FavoritesBusiness {

	
	@Autowired
	UserDAO userRepository;
	
	@Autowired
	FavoriteDAO favoriteRepository;
	
	@Autowired
	ProductDAO productRepository;
	
	@Override
	public List<Favorite> findByEmail(String email) {
		// TODO Auto-generated method stub
		 Optional<User> userOptional = userRepository.findByEmail(email);
		    if (userOptional.isPresent()) {
		        return favoriteRepository.findByUser(userOptional.get());
		    }
		    return Collections.emptyList();
	}

	@Override
	public int countByProductId(Long productId) {
		// TODO Auto-generated method stub
		if (productRepository.existsById(productId)) {
	        return favoriteRepository.countByProduct(productRepository.getById(productId));
	    }
	    return 0;
	}

	@Override
	public Favorite findByProductAndUser(Long productId, String email) {
		// TODO Auto-generated method stub
		Optional<User> userOptional = userRepository.findByEmail(email);
	    if (userOptional.isPresent()) {
	        Optional<Product> productOptional = productRepository.findById(productId);
	        if (productOptional.isPresent()) {
	            User user = userOptional.get();
	            Product product = productOptional.get();
	            return favoriteRepository.findByProductAndUser(product, user);
	        }
	    }
		return null;
	}

	@Override
	public Favorite saveFavorite(Favorite favorite) {
		// TODO Auto-generated method stub
		return favoriteRepository.save(favorite);
	}

	@Override
	public boolean deleteFavoriteById(Long id) {
		if (favoriteRepository.existsById(id)) {
	        favoriteRepository.deleteById(id);
	        return true;
	    }
	    return false;
	}
}
