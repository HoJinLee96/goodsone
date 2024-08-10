package api;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dto.AddressDto;
import dto.UserCredentials;
import dto.UserDto;
import exception.NotFoundException;
import service.AddressServices;
import service.KakaoOAuthLoginService;
import service.NaverOAuthLoginService;
import service.UserServices;

@RestController
@RequestMapping("/api")
public class LoginController {

	private UserServices userServices;
	private AddressServices addressServices;

	@Autowired
	public LoginController(UserServices userServices, AddressServices addressServices,NaverOAuthLoginService naverOAuthLoginService,KakaoOAuthLoginService kakaoOAuthLoginService) {
	    this.userServices = userServices;
	    this.addressServices = addressServices;
	  }

	@PostMapping("/loginByEmail")
	public ResponseEntity<String> loginByEmail(@RequestParam String reqEmail, @RequestParam String reqPassword,
			HttpSession session) {
		System.out.println("LoginController.loginByEmail() 시작");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/plain; charset=UTF-8");

		
		try {
		  UserCredentials userCredentials = userServices.getPasswordByEmail(reqEmail);
			if (userCredentials.validatePassword(reqPassword)) {
				System.out.println("로그인 성공");
				UserDto user = userServices.getUserBySeq(userCredentials.getUserSeq());
				List<AddressDto> addressList = addressServices.getListByUserSeq(userCredentials.getUserSeq());
//				session.invalidate();
				session.setAttribute("user", user);
				session.setAttribute("addressList", addressList);
				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
				return ResponseEntity.ok("로그인 성공");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("비밀번호가 일치하지 않습니다.");
			}
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("일치하는 회원정보가 없습니다.");
		} catch (SQLException e) {
		  e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 접속할 수 없습니다.");
		}
	}

    
}
