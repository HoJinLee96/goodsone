package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dto.User;
import service.UserService;

@Controller
public class WebMainController {

	private UserService userService;

	@Autowired
	public WebMainController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/home")
	public String showHome() {
		return "main_home";
	}

	@RequestMapping("/login")
	public String showLogin(@ModelAttribute("user")String user) {
	    if (user != null) {
	        return "redirect:/home"; // 이미 로그인된 경우 홈 페이지로 리다이렉트
	    }
	    return "login"; // 로그인되지 않은 경우 로그인 페이지 제공
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam String email, 
	                    @RequestParam String password, 
	                    HttpServletRequest request, 
	                    Model model) {
		
//		String userSeq = userService.getUserByEmail(email);
		
	    // 예시: 사용자를 인증하는 로직
	    if ("email".equals(email) && "password".equals(password)) {
	        HttpSession session = request.getSession();
	        session.setAttribute("user", email);
	        session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
	        
	        // 원래 요청 URL로 리다이렉트
	        String redirectURI = (String) session.getAttribute("redirectURI");
	        if (redirectURI != null) {
	            session.removeAttribute("redirectURI");
	            return "redirect:" + redirectURI;
	        }
	        return "redirect:/home";
	    } else {
	        model.addAttribute("error", "Invalid username or password");
	        return "login";
	    }
	}

	@RequestMapping("/join")
	public String showJoin() {
		return "join";
	}

	@RequestMapping("/register")
	public String registerUser(@RequestParam("userEmail") String userEmail,
			@RequestParam("userPassword") String userPassword,
			@RequestParam("userConfirmPassword") String userConfirmPassword, @RequestParam("userName") String userName,
			@RequestParam("userNickname") String userNickname, @RequestParam("birthYear") int birthYear,
			@RequestParam("birthMonth") int birthMonth, @RequestParam("birthDay") int birthDay,
			@RequestParam("userPhoneAgency") String userPhoneAgency,
			@RequestParam("userPhoneNumber") String userPhoneNumber, @RequestParam("userAddress") String userAddress) {

		// 비밀번호 확인 로직
		if (!userPassword.equals(userConfirmPassword)) {
			return "redirect:/goodsone1/register?error=passwordMismatch";
		}

		// 생년월일 형식으로 합치기
		String userBirth = String.format("%04d-%02d-%02d", birthYear, birthMonth, birthDay);

		// 사용자 생성 및 저장 로직 (예: UserService를 통한 저장)
//		User user = new User(userEmail, userPassword, userName, userNickname, userBirth, userPhoneAgency,
//				userPhoneNumber, userAddress);
//		userService.saveUser(user);

		// 회원가입 성공 시 로그인 페이지로 리다이렉트
		return "redirect:/login";
	}

	@RequestMapping("/performLogin")
	public String performLogin(HttpSession session, String email, String password) {
		User user = userService.getUserByEmail(email).get();

		if (user.getUserEmail().equals(email) && user.getUserPassword().equals(password)) {
			// 로그인 성공, 세션에 사용자 정보를 저장
			session.setAttribute("user", user.getUserSeq());
			return "redirect:/home";
		} else {
			// 로그인 실패
			return "redirect:/login";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 세션에서 사용자 정보를 제거하여 로그아웃 처리
		session.invalidate();
		return "redirect:/home";
	}
}
