package vn.fs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.fs.entity.Cart;
import vn.fs.entity.User;

@Repository
public interface CartDAO extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
