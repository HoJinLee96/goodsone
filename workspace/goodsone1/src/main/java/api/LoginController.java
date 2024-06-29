package api;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.User;
import exception.UserNotFoundException;
import service.UserService;

@Component
@Controller
@RequestMapping("/api")
public class LoginController {

	private UserService userService;
	
	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password, HttpSession session) {
System.out.println("login() 시작");
		User user;
		try {
			user = userService.getUserByEmail(email);
			if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
				System.out.println("로그인 성공");
				session.setAttribute("userSeq", Long.valueOf(user.getUserSeq()));
				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
                return ResponseEntity.ok("Login successful");
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
			}
		} catch (UserNotFoundException | SQLException e) {
			//@ControllerAdvice 처리
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}


	}
}
