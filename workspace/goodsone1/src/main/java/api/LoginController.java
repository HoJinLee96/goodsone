package api;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dto.UserCredentials;
import dto.UserDto;
import exception.NotFoundException;
import service.AddressServices;
import service.UserServices;

@Controller
public class LoginController {

	private UserServices userServices;
	private AddressServices addressServices;

	@Autowired
	public LoginController(UserServices userServices, AddressServices addressServices) {
	    this.userServices = userServices;
	    this.addressServices = addressServices;
	  }

	@PostMapping("/loginByEmail")
	public String loginByEmail(
	    @RequestParam("email") String reqEmail, 
	    @RequestParam("password") String reqPassword,
        HttpSession session,
        HttpServletRequest request,
        RedirectAttributes redirectAttributes) {
		System.out.println("LoginController.loginByEmail() 시작");
		
		try {
		  UserCredentials userCredentials = userServices.getPasswordByEmail(reqEmail);
			if (userCredentials.validatePassword(reqPassword)) {
				System.out.println("로그인 성공");
				UserDto userDto = userServices.getUserBySeq(userCredentials.getUserSeq());
//				List<AddressDto> addressList = addressServices.getListByUserSeq(userCredentials.getUserSeq());
//				session.invalidate();
				session.setAttribute("userDto", userDto);
//				session.setAttribute("addressList", addressList);
				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
				
				 // 이전 페이지의 도메인 확인
	            String referer = request.getHeader("Referer");
	            if (referer != null && referer.startsWith(request.getScheme() + "://" + request.getServerName())) {
	                return "redirect:" + referer;  // 이전 페이지로 리다이렉트
	            } else {
	                return "redirect:/home";  // 기본 페이지로 리다이렉트
	            }
			} else {
			  redirectAttributes.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
	            return "redirect:/login";  // 로그인 페이지로 리다이렉트
			}
		} catch (NotFoundException e) {
		  redirectAttributes.addFlashAttribute("message", "일치하는 회원정보가 없습니다.");
	        return "redirect:/login";
		} catch (SQLException e) {
		  e.printStackTrace();
	        redirectAttributes.addFlashAttribute("message", "현재 접속할 수 없습니다.");
	        return "redirect:/login";
		}
	}

    
}
