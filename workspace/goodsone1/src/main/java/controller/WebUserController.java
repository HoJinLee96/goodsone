package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.UserService;

@Controller
@RequestMapping
public class WebUserController {
	
	private UserService userService;
	
	@Autowired
	public WebUserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/my")
	public String showMyPage(HttpSession session) {
		if (session.getAttribute("userSeq") != null) {
			// 로그인 상태
			return "my"; // myPage.jsp로 이동
		} else {
			// 비로그인 상태, 로그인 페이지로 리디렉션
			return "redirect:/login"; // login.jsp로 리디렉션
		}
	}
	
	
}
