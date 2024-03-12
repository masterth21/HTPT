package vn.fs.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import vn.fs.dto.LoginRequest;
import vn.fs.dto.SignupRequest;
import vn.fs.entity.User;

public interface UserBusiness {

	List<User> getAllActiveUsers();
	
	User getUserById(Long id);
	
	User getUserByEmail(String email);
	
	ResponseEntity<User> createUser(User user);
	
	ResponseEntity<User> updateUser(Long id, User user);
	
	ResponseEntity<User> updateUserAsAdmin(Long id, User user);
	
	public boolean deleteById(Long id);
	
	ResponseEntity<Void> deleteUserById(Long id);
	
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
	
	public ResponseEntity<?> registerUser(SignupRequest signupRequest);
	
	public ResponseEntity<Void> logout();
	
	public void sendToken(String email, String token);
	
}
