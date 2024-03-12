package vn.fs.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

@Service
public class UserBusinessImpl implements UserBusiness {

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

	
	@Override
	public List<User> getAllActiveUsers() {
		// TODO Auto-generated method stub
		return userRepository.findByStatusTrue();
	}


	@Override
	public User getUserById(Long id) {
		// Truy vấn người dùng theo ID
        Optional<User> userOptional = userRepository.findById(id);
        // Kiểm tra xem người dùng có tồn tại không
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null; // Hoặc bạn có thể throw một exception tùy ý ở đây
        }
	}


	@Override
	public User getUserByEmail(String email) {
		// Truy vấn người dùng theo email
        Optional<User> userOptional = userRepository.findByEmail(email);
        // Kiểm tra xem người dùng có tồn tại không
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null; // Hoặc bạn có thể throw một exception tùy ý ở đây
        }
	}


	@Override
	public ResponseEntity<User> createUser(User user) {
		// Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.notFound().build();
        }
        // Kiểm tra xem userId đã tồn tại chưa
        if (userRepository.existsById(user.getUserId())) {
            return ResponseEntity.badRequest().build();
        }

        // Thêm role cho user (ví dụ)
        Set<AppRole> roles = new HashSet<>();
        roles.add(new AppRole(1, null));
        user.setRoles(roles);

        // Mã hóa password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Tạo token
        user.setToken(jwtUtils.doGenerateToken(user.getEmail()));

        // Lưu thông tin user
        User savedUser = userRepository.save(user);

        // Tạo cart cho user và lưu vào database
        Cart cart = new Cart(0L, 0.0, user.getAddress(), user.getPhone(), user);
        cartRepository.save(cart);

        return ResponseEntity.ok(savedUser);
    }


	@Override
	public ResponseEntity<User> updateUser(Long id, User user) {
		// Kiểm tra xem user có tồn tại không
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // Kiểm tra xem id của user có khớp với id được gửi qua không
        if (!id.equals(user.getUserId())) {
            return ResponseEntity.badRequest().build();
        }

        // Lấy thông tin user cũ từ database
        User temp = userRepository.findById(id).get();

        // Nếu mật khẩu đã thay đổi, mã hóa mật khẩu mới
        if (!user.getPassword().equals(temp.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        // Thêm role cho user (ví dụ)
        Set<AppRole> roles = new HashSet<>();
        roles.add(new AppRole(1, null));
        user.setRoles(roles);

        // Lưu thông tin user đã cập nhật vào database
        return ResponseEntity.ok(userRepository.save(user));
	}


	@Override
	public ResponseEntity<User> updateUserAsAdmin(Long id, User user) {
		// Kiểm tra xem user có tồn tại không
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Kiểm tra id của user có khớp với id trong path không
        if (!id.equals(user.getUserId())) {
            return ResponseEntity.badRequest().build();
        }

        // Thiết lập role của user là admin
        Set<AppRole> roles = new HashSet<>();
        roles.add(new AppRole(2, null));
        user.setRoles(roles);

        // Lưu thông tin user
        return ResponseEntity.ok(userRepository.save(user));
	}


	@Override
	public ResponseEntity<Void> deleteUserById(Long id) {
		// Kiểm tra xem user có tồn tại không
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Lấy user từ repository và đặt trạng thái của user thành false
        User user = userRepository.findById(id).get();
        user.setStatus(false);
        userRepository.save(user);

        return ResponseEntity.ok().build();
	}


	@Override
	public boolean deleteById(Long id) {
		if (!userRepository.existsById(id)) {
	        return false;
	    }
	    User user = userRepository.findById(id).orElse(null);
	    if (user != null) {
	        user.setStatus(false);
	        userRepository.save(user);
	        return true;
	    }
	    return false;
	}


	@Override
	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
		try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
                userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
                userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(), userDetails.getRegisterDate(),
                roles));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
	}


	@Override
	public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
		 try {
	            if (userRepository.existsByEmail(signupRequest.getEmail())) {
	                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
	            }

	            // create new user account
	            User user = new User(signupRequest.getName(), signupRequest.getEmail(),
	                    passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
	                    signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
	                    signupRequest.getImage(), signupRequest.getRegisterDate(),
	                    jwtUtils.doGenerateToken(signupRequest.getEmail()));
	            Set<AppRole> roles = new HashSet<>();
	            roles.add(new AppRole(1, null));

	            user.setRoles(roles);
	            userRepository.save(user);
	            return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: Registration failed!"));
	        }
	}


	@Override
	public ResponseEntity<Void> logout() {
		 SecurityContextHolder.clearContext();
	     return ResponseEntity.ok().build();
	}


	@Override
	public void sendToken(String email, String token) {
		String title = "Reset mật khẩu";
        String body = "<h2>Hãy nhấp vào link để thay đổi mật khẩu của bạn</h2>"
                    + "<a href=\"http://localhost:8080/forgot-password/" + token + "\">Đổi mật khẩu</a>";
        sendMailService.queue(email, title, body);
		
	}
	
	
	

}
