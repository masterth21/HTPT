package vn.fs.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.config.JwtUtils;
import vn.fs.dto.JwtResponse;
import vn.fs.dto.LoginRequest;
import vn.fs.dto.MessageResponse;
import vn.fs.dto.SignupRequest;
import vn.fs.entity.AppRole;
import vn.fs.entity.Cart;
import vn.fs.entity.User;
import vn.fs.repository.AppRoleDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.UserDAO;
import vn.fs.service.SendMailBusiness;
import vn.fs.service.UserBusiness;
import vn.fs.service.implement.UserDetailsImpl;

@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class UserService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDAO userRepository;

	@Autowired
	CartDAO cartRepository;

	@Autowired
	AppRoleDAO roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	SendMailBusiness sendMailService;

	@Autowired
	UserBusiness userService;
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
//		return ResponseEntity.ok(userRepository.findByStatusTrue());
		List<User> activeUsers = userService.getAllActiveUsers();
	    // Trả về danh sách người dùng trong ResponseEntity
	    return ResponseEntity.ok(activeUsers);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getOne(@PathVariable("id") Long id) {
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(userRepository.findById(id).get());
		// Gọi service method để lấy thông tin của người dùng
	    User user = userService.getUserById(id);
	    // Kiểm tra xem người dùng có tồn tại không
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("email/{email}")
	public ResponseEntity<User> getOneByEmail(@PathVariable("email") String email) {
//		if (userRepository.existsByEmail(email)) {
//			return ResponseEntity.ok(userRepository.findByEmail(email).get());
//		}
//		return ResponseEntity.notFound().build();
		User user = userService.getUserByEmail(email);
	    // Kiểm tra xem người dùng có tồn tại không
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@PostMapping
	public ResponseEntity<User> post(@RequestBody User user) {
//		if (userRepository.existsByEmail(user.getEmail())) {
//			return ResponseEntity.notFound().build();
//		}
//		if (userRepository.existsById(user.getUserId())) {
//			return ResponseEntity.badRequest().build();
//		}
//
//		Set<AppRole> roles = new HashSet<>();
//		roles.add(new AppRole(1, null));
//
//		user.setRoles(roles);
//
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//		user.setToken(jwtUtils.doGenerateToken(user.getEmail()));
//		User u = userRepository.save(user);
//		Cart c = new Cart(0L, 0.0, u.getAddress(), u.getPhone(), u);
//		cartRepository.save(c);
//		return ResponseEntity.ok(u);
		return userService.createUser(user);
	}

	@PutMapping("{id}")
	public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!id.equals(user.getUserId())) {
//			return ResponseEntity.badRequest().build();
//		}
//
//		User temp = userRepository.findById(id).get();
//
//		if (!user.getPassword().equals(temp.getPassword())) {
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//		}
//
//		Set<AppRole> roles = new HashSet<>();
//		roles.add(new AppRole(1, null));
//
//		user.setRoles(roles);
//		return ResponseEntity.ok(userRepository.save(user));
		return userService.updateUser(id, user);
	}

	@PutMapping("admin/{id}")
	public ResponseEntity<User> putAdmin(@PathVariable("id") Long id, @RequestBody User user) {
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		if (!id.equals(user.getUserId())) {
//			return ResponseEntity.badRequest().build();
//		}
//		Set<AppRole> roles = new HashSet<>();
//		roles.add(new AppRole(2, null));
//
//		user.setRoles(roles);
//		return ResponseEntity.ok(userRepository.save(user));
		return userService.updateUserAsAdmin(id, user);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//		User u = userRepository.findById(id).get();
//		u.setStatus(false);
//		userRepository.save(u);
//		return ResponseEntity.ok().build();
		
		boolean deleted = userService.deleteById(id);
	    if (!deleted) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().build();
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
//				userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
//				userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(), userDetails.getRegisterDate(),
//				roles));
		
		return userService.authenticateUser(loginRequest);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {

//		if (userRepository.existsByEmail(signupRequest.getEmail())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
//		}
//
//		if (userRepository.existsByEmail(signupRequest.getEmail())) {
//			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
//		}
//
//		// create new user account
//		User user = new User(signupRequest.getName(), signupRequest.getEmail(),
//				passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
//				signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
//				signupRequest.getImage(), signupRequest.getRegisterDate(),
//				jwtUtils.doGenerateToken(signupRequest.getEmail()));
//		Set<AppRole> roles = new HashSet<>();
//		roles.add(new AppRole(1, null));
//
//		user.setRoles(roles);
//		userRepository.save(user);
//		Cart c = new Cart(0L, 0.0, user.getAddress(), user.getPhone(), user);
//		cartRepository.save(c);
//		return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
		return userService.registerUser(signupRequest);

	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout() {
//		return ResponseEntity.ok().build();
		return userService.logout();
	}

	@PostMapping("send-mail-forgot-password-token")
	public ResponseEntity<String> sendToken(@RequestBody String email) {

//		if (!userRepository.existsByEmail(email)) {
//			return ResponseEntity.notFound().build();
//		}
//		User user = userRepository.findByEmail(email).get();
//		String token = user.getToken();
//		sendMaiToken(email, token, "Reset mật khẩu");
//		return ResponseEntity.ok().build();
		if (!userRepository.existsByEmail(email)) {
	        return ResponseEntity.notFound().build();
	    }
	    User user = userRepository.findByEmail(email).get();
	    String token = user.getToken();
	    userService.sendToken(email, token);
	    return ResponseEntity.ok().build();

	}

//	public void sendMaiToken(String email, String token, String title) {
//		String body = "\r\n" + "    <h2>Hãy nhấp vào link để thay đổi mật khẩu của bạn</h2>\r\n"
//				+ "    <a href=\"http://localhost:8080/forgot-password/" + token + "\">Đổi mật khẩu</a>";
//		sendMailService.queue(email, title, body);
//	}

}
