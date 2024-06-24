package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dto.User;
import service.UserServiceImpl;

@Controller
@Component
public class WebMainController {
	
	private UserServiceImpl userService;

	@Autowired
	public WebMainController(UserServiceImpl userServiceImpl) {
		this.userService = userServiceImpl;
	}

	@RequestMapping("/home")
	public String showHome() {
		return "main_home"; 
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login"; // login.jsp로 이동
	}

	@RequestMapping("/join")
	public String showJoin() {
		return "join"; // login.jsp로 이동
	}

	@RequestMapping("/performLogin")
	public String performLogin(HttpSession session, String email, String password) {

		User user = userService.getUserByEmail(email).get();
		
		if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
			// 로그인 성공, 세션에 사용자 정보를 저장
			session.setAttribute("user", email);
			return "redirect:/main_home"; // 로그인 후 my.jsp로 리디렉션
		} else {
			// 로그인 실패
			return "redirect:/login"; // 로그인 실패 시 다시 로그인 페이지로 리디렉션
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 세션에서 사용자 정보를 제거하여 로그아웃 처리
		session.invalidate();
		return "redirect:/main_home"; // 로그아웃 후 로그인 페이지로 리디렉션
	}
}
