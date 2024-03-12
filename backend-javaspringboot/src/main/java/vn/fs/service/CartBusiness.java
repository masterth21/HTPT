package vn.fs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import vn.fs.entity.Cart;
import vn.fs.entity.User;
import vn.fs.repository.CartDetailDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.UserDAO;

//@Service
public interface CartBusiness {
	Cart getCartUser(String email);
	
	Cart putCartUser(String email, Cart cart);
}
