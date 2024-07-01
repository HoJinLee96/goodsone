package api;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

	@PostMapping("/loginByEmail")
	public ResponseEntity<String> loginByEmail(@RequestParam String email, @RequestParam String password,
			HttpSession session) {
		System.out.println("LoginController.loginByEmail() 시작");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain; charset=UTF-8");

		User user;
		try {
			user = userService.getUserByEmail(email);
			if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
				System.out.println("로그인 성공");
				System.out.println(user.getUserSeq() + "세션에 저장");
				session.setAttribute("userSeq", user.getUserSeq()+"");
				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
				return ResponseEntity.ok("Login successful");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("비밀번호가 일치하지 않습니다.");
			}
		} catch (UserNotFoundException e) {
			// @ControllerAdvice 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("일치하는 회원정보가 없습니다.");
		} catch (SQLException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 접속할 수 없습니다.");
		}
	}

	@PostMapping("/loginBySeq")
	public ResponseEntity<String> loginBySeq(@RequestParam String reqPassword, HttpSession session) {
		System.out.println("LoginController.loginBySeq() 시작");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain; charset=UTF-8");

		int seq = Integer.valueOf((String) session.getAttribute("userSeq")); 
		System.out.println(seq+" / ");
		String password;
		try {
			password = userService.getPasswordBySeq(seq);
			if (password.equals(reqPassword)) {
				System.out.println("로그인 성공");
				session.setAttribute("loginBySeq", seq+"");
				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
				return ResponseEntity.ok("Login successful");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("비밀번호가 일치하지 않습니다.");
			}
		} catch (UserNotFoundException e) {
			// @ControllerAdvice 처리
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("일치하는 회원정보가 없습니다.");
		} catch (SQLException e) {
		  e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 접속할 수 없습니다.");
		}
	}
}
