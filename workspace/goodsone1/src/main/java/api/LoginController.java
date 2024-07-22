package api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AddressDto;
import dto.NaverCallback;
import dto.NaverRes;
import dto.NaverToken;
import dto.UserCredentials;
import dto.UserDto;
import exception.UserNotFoundException;
import service.AddressServices;
import service.OAuthLoginService;
import service.UserServices;

@RestController
@RequestMapping("/api")
public class LoginController {

	private UserServices userServices;
	private AddressServices addressServices;
	private OAuthLoginService oAuthLoginService;

	@Autowired
	public LoginController(UserServices userServices, AddressServices addressServices,OAuthLoginService oAuthLoginService) {
	    this.userServices = userServices;
	    this.addressServices = addressServices;
	    this.oAuthLoginService = oAuthLoginService;
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
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("일치하는 회원정보가 없습니다.");
		} catch (SQLException e) {
		  e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 접속할 수 없습니다.");
		}
	}

//  @PostMapping("/loginBySession")
//	public ResponseEntity<String> loginBySession(@RequestParam String reqPassword,HttpSession session){
//	  HttpHeaders headers = new HttpHeaders();
//      headers.add("Content-Type", "text/plain; charset=UTF-8");
//
//	  UserCredentials userCredentials = (UserCredentials) session.getAttribute("userCredentials");
//	  if(userCredentials.validatePassword(reqPassword)){
//	    System.out.println("restful api loginByUserCredentials 성공");
//	    return ResponseEntity.ok("Login successful");
//	  }
//      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("비밀번호가 일치하지 않습니다.1");
//	  
//	}

//	@PostMapping("/loginBySeq")
//	public ResponseEntity<String> loginBySeq(@RequestParam String reqPassword, HttpSession session) {
//		System.out.println("LoginController.loginBySeq() 시작");
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "text/plain; charset=UTF-8");
//
//		int seq = Integer.valueOf((String) session.getAttribute("userSeq")); 
//		System.out.println(seq+" / ");
//		String password;
//		try {
//			password = userServices.getPasswordBySeq(seq);
//			if (password.equals(reqPassword)) {
//				System.out.println("로그인 성공");
//				session.setAttribute("loginBySeq", seq+"");
//				session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
//				return ResponseEntity.ok("Login successful");
//			} else {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("비밀번호가 일치하지 않습니다.");
//			}
//		} catch (UserNotFoundException e) {
//			// @ControllerAdvice 처리
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body("일치하는 회원정보가 없습니다.");
//		} catch (SQLException e) {
//		  e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 접속할 수 없습니다.");
//		}
//	}
	
    @GetMapping("/naver-login")
    public void naverLogin(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        String url = oAuthLoginService.getNaverAuthorizeUrl("authorize");
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @GetMapping("/login/naver")
    public void callBack(HttpServletRequest request, HttpServletResponse response, NaverCallback callback) throws URISyntaxException, IOException {

        if (callback.getError() != null) {
            System.out.println(callback.getError_description());
        }

        String responseToken = oAuthLoginService.getNaverTokenUrl("token", callback);

        ObjectMapper mapper = new ObjectMapper();
        NaverToken token = mapper.readValue(responseToken, NaverToken.class);
        System.out.println("token.getExpires_in() : "+token.getExpires_in());
        String responseUser = oAuthLoginService.getNaverUserByToken(token);
        NaverRes naverUser = mapper.readValue(responseUser, NaverRes.class);

        System.out.println("naverUser.toString() : " + naverUser.toString());
        System.out.println("naverUser.getResonse().getGender() : " + naverUser.getResponse().getGender());
        System.out.println("naverUser.getResonse().getBirthyear() : " + naverUser.getResponse().getBirthyear());
        System.out.println("naverUser.getResonse().getAge() : " + naverUser.getResponse().getAge());

    }
    
    @GetMapping("/logout/naver")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException, JsonProcessingException {

        if (callback.getError() != null) {
            System.out.println(callback.getError_description());
        }

        String responseToken = oAuthLoginService.getNaverTokenUrl("token", callback);

        ObjectMapper mapper = new ObjectMapper();
        NaverToken token = mapper.readValue(responseToken, NaverToken.class);
        String responseUser = oAuthLoginService.getNaverUserByToken(token);
        NaverRes naverUser = mapper.readValue(responseUser, NaverRes.class);

        System.out.println("naverUser.toString() : " + naverUser.toString());
        System.out.println("naverUser.getResonse().getGender() : " + naverUser.getResponse().getGender());
        System.out.println("naverUser.getResonse().getBirthyear() : " + naverUser.getResponse().getBirthyear());
        System.out.println("naverUser.getResonse().getAge() : " + naverUser.getResponse().getAge());

    }
}
