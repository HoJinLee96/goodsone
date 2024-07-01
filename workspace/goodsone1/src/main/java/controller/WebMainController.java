package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebMainController {

	@GetMapping("/home")
	public String showHome() {
		System.out.println("WebMainController.showHome() 실행");
		return "main_home";
	}

	@GetMapping("/login")
	public String showLogin() {
		System.out.println("WebMainController.showLogin() 실행");
		return "login";
	}

	@GetMapping("/join")
	public String showJoin() {
		System.out.println("WebMainController.showJoin() 실행");
		return "join";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("WebMainController.logout() 실행");
// 		세션에서 사용자 정보를 제거하여 로그아웃 처리
		if (session != null) {
			session.removeAttribute("userSeq");
			}
		return "redirect:/home";
	}

	@GetMapping("/my")
	public String showMy(HttpSession session) {
		System.out.println("WebMainController.showMy() 실행");
		return "my";
	}

}
