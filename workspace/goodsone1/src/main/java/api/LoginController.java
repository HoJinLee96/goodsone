package api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import dto.KakaoUserInfoResponseDto;
import dto.OAuthDto;
import dto.UserCredentials;
import dto.UserDto;
import dtoNaverLogin.Callback;
import dtoNaverLogin.NaverRes;
import dtoNaverLogin.OAuthToken;
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
	private NaverOAuthLoginService naverOAuthLoginService;
	private KakaoOAuthLoginService kakaoOAuthLoginService;

	@Autowired
	public LoginController(UserServices userServices, AddressServices addressServices,NaverOAuthLoginService naverOAuthLoginService,KakaoOAuthLoginService kakaoOAuthLoginService) {
	    this.userServices = userServices;
	    this.addressServices = addressServices;
	    this.naverOAuthLoginService = naverOAuthLoginService;
	    this.kakaoOAuthLoginService = kakaoOAuthLoginService;
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
	
    @GetMapping("/naver-login")
    public void naverLogin(HttpServletRequest req, HttpServletResponse res) throws MalformedURLException, UnsupportedEncodingException, URISyntaxException {
        String url = naverOAuthLoginService.getNaverAuthorizeUrl("authorize");
        try {
          res.sendRedirect(url);
        } catch (Exception e) {
            e.printStackTrace();
            try {
              res.setContentType("text/html;charset=UTF-8");
              res.getWriter().write("<script>alert('현재 네이버 로그인을 이용 할 수 없습니다.'); window.location.href='/login';</script>");
            } catch (IOException ex) {
              ex.printStackTrace();
            }
        }
    }
    
    @GetMapping("/login/naver")
    public ResponseEntity<String> callBack(HttpServletRequest request, HttpServletResponse response, Callback callback) {
      System.out.println("LoginController.callBack() 시작");
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "text/plain; charset=UTF-8");
      
        if (callback.getError() != null) {
            System.out.println(callback.getError_description());
            headers.setLocation(URI.create("/home"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
        }
        
        OAuthToken oAuthToken = null;
        NaverRes naverUser = null;
        try {
          // 접근 토큰 발급
          String responseToken = naverOAuthLoginService.getTokenUrl("token",callback);
          ObjectMapper mapper = new ObjectMapper();
          oAuthToken = mapper.readValue(responseToken, OAuthToken.class);
          
          // 계정 정보 발급
          String responseUser = naverOAuthLoginService.getNaverUserByToken(oAuthToken);
          naverUser = mapper.readValue(responseUser, NaverRes.class);
        } catch (URISyntaxException | IOException e) {
          e.printStackTrace();
          headers.setLocation(URI.create("/login"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
        }
        
        // 계정 고유 id
        String oAuthid = naverUser.getResponse().getId();
        
        HttpSession session = request.getSession();
        try {
          // 계정 고유 id통해 oauth테이블에 데이터 있는지 확인 -> 없으면 NotFoundException 발생
          OAuthDto oAuthDto = userServices.getOAuthByOAuthId("NAVER", oAuthid);
            if(oAuthDto.getUserSeq()!=0) {// 데이터 존재 (기존 회원 계정과 소셜 계정이 연동된 계정)
              // 해당 데이터의 userSeq 추출 및 user 테이블 데이터 읽기
            UserDto userDto = userServices.getUserBySeq(oAuthDto.getUserSeq()); // 여기서도 NotFoundException 발생 가능성이 있긴한데 여기서 발생시 로직에 문제가 있는거임.
            // 세션에 user 등록
            session.setAttribute("user", userDto);
            }
            // 세션에 토큰,OAuthDto 등록 (기존 회원에 연동된 계정이든 소셜 전용 계정이든 세션에 토큰 등록)
          session.setAttribute("oAuthDto", oAuthDto);
          session.setAttribute("oAuthToken", oAuthToken);
          session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in()) * 1000));
          
          scheduleTokenRefresh("NAVER",session, oAuthToken);

          headers.setLocation(URI.create("/home"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("로그인 성공");
        }
        
        catch (NotFoundException e) { //OAuth 처음 이용자 / oauth 테이블에 데이터 없음 등록 절차 진행
          try {
            System.out.println("OAuth 처음 이용자");
            userServices.registerOAuth("NAVER", new OAuthDto(naverUser));
            session.setAttribute("oAuthToken", oAuthToken);
            session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in()) * 1000));
            headers.setLocation(URI.create("/home"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("회원가입 성공");
          } catch (SQLException | NotFoundException e1) {
            e1.printStackTrace();
            headers.setLocation(URI.create("/home"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
          }
        }
         catch (SQLException e) {
          e.printStackTrace();
          headers.setLocation(URI.create("/home"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");

        }
    }
    
    @GetMapping("/refresh/naver")
    public ResponseEntity<String> naverRefreshToken(HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain; charset=UTF-8");
      
//        HttpSession session = request.getSession(false);
        if (session == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("세션이 없습니다.");
        }
        OAuthDto oAuthDto = (OAuthDto)session.getAttribute("oAuthDto");
        if (oAuthDto == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("인증정보가 없습니다.");
        }
        OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
        if (oAuthToken == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰이 없습니다.");
        }
        Long oAuthTokenExpiry = (Long) session.getAttribute("oAuthTokenExpiry");
        if (oAuthTokenExpiry == null || System.currentTimeMillis() > oAuthTokenExpiry) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰 유효기간이 지났습니다.");
        }
        
        // 리프레시 토큰으로 새로운 엑세스 토큰 요청
        OAuthToken  newToken = null;
        try {
          String responseToken = naverOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
          ObjectMapper mapper = new ObjectMapper();
          newToken = mapper.readValue(responseToken, OAuthToken.class);
        } catch (URISyntaxException | IOException e) {
          e.printStackTrace();
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰 갱신 실패");
        }

        // 새로운 토큰을 세션에 저장
        session.setAttribute("oAuthToken", newToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(newToken.getExpires_in())  * 1000));
        System.out.println("갱신 완료");
        return ResponseEntity.ok("토큰 갱신 성공");
    }
    
    private void scheduleTokenRefresh(String provider,HttpSession session, OAuthToken oAuthToken) {
        long expiryTime = Integer.parseInt(oAuthToken.getExpires_in()) * 1000;
        System.out.println("expiryTime : " + expiryTime);
        Timer timer = new Timer();
        
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                  if(provider.equals("NAVER"))
                    naverRefreshToken(session);
                  else if(provider.equals("KAKAO"))
                    kakaoRefreshToken(session);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//        }, expiryTime - 60000);
        }, 10000,10000); //10초 마다 갱신하기, 테스트용
    }

    
    @GetMapping("/delete/naver")
    public ResponseEntity<String> deleteToken(HttpServletRequest request) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "text/plain; charset=UTF-8");
      
        HttpSession session = request.getSession(false);
        if (session == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("세션이 없습니다.");
        }
        OAuthDto oAuthDto = (OAuthDto)session.getAttribute("oAuthDto");
        if (oAuthDto == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("인증정보가 없습니다.");
        }
        OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
        if (oAuthToken == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰이 없습니다.");
        }
        Long oAuthTokenExpiry = (Long) session.getAttribute("oAuthTokenExpiry");
        if (oAuthTokenExpiry == null || System.currentTimeMillis() > oAuthTokenExpiry) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰 유효기간이 지났습니다.");
        }
        
        
        OAuthToken deleteToken =null;
        try {
          String responseToken = naverOAuthLoginService.updateTokenUrl("token","delete",oAuthToken);
          ObjectMapper mapper = new ObjectMapper();
          deleteToken = mapper.readValue(responseToken, OAuthToken.class);
        } catch (IOException | URISyntaxException e) {
          e.printStackTrace();
          headers.setLocation(URI.create("/my"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 회원탈퇴 할 수 없는 계정입니다.");
        }
        
        String result = deleteToken.getResult();
        if(result.equals("success")) {
          try {
            userServices.deleteOAuthDtoByOAuthId(oAuthDto.getId());
            session.invalidate();
//            session.removeAttribute("oAuthDto");
//            session.removeAttribute("oAuthToken");
//            session.removeAttribute("oAuthTokenExpiry");
            System.out.println("회원탈퇴 완료");
            headers.setLocation(URI.create("/home"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("회원탈퇴 완료");
          } catch (SQLException | NotFoundException e) {
            headers.setLocation(URI.create("/my"));
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 회원탈퇴 할 수 없는 계정입니다.");
          }
        } else {
          System.out.println(deleteToken.getError() + deleteToken.getError_description());
          headers.setLocation(URI.create("/my"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 회원탈퇴 할 수 없는 계정입니다.");
        }
        
    }

    
    @GetMapping("/kakaoLoginUrl")
    public void kakaoLoginUrl(HttpServletRequest req, HttpServletResponse res) {
      System.out.println("LoginController.kakaoLogin() 실행");

        String url;
        try {
          url = kakaoOAuthLoginService.getKakaoLoginUrl("authorize");
          res.sendRedirect(url);
        } catch (URISyntaxException | IOException e) {
          e.printStackTrace();
          try {
            res.setContentType("text/html;charset=UTF-8");
            res.getWriter().write("<script>alert('현재 카카오 로그인을 이용 할 수 없습니다.'); window.location.href='/login';</script>");
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
    }
    
    @GetMapping("/login/kakao")
    public ResponseEntity<String> loginKakaoCallBack(HttpServletRequest req, HttpServletResponse res,@RequestParam(value = "code", defaultValue = "defaultCode") String code,
        @RequestParam(value = "error", defaultValue = "defaultValue") String error) {
      System.out.println("LoginController.loginKakaoCallBack() 실행");
      
      HttpHeaders headers = new HttpHeaders();
//      headers.add("Content-Type", "text/plain; charset=UTF-8");
      
      if (!error.equals("defaultValue")) {
        System.out.println(error);
        headers.setLocation(URI.create("/login"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 카카오 로그인을 이용 할 수 없습니다.");
      }
    
      //인가코드 통해 접근 토큰 얻기
      ObjectMapper mapper = new ObjectMapper();
      OAuthToken oAuthToken = new OAuthToken();
      String responseBody = "";
      try {
        responseBody = kakaoOAuthLoginService.getKakaoToken("token",code);
        oAuthToken = mapper.readValue(responseBody, OAuthToken.class);
        System.out.println(oAuthToken.toString());
        
      } catch (JsonProcessingException | NotFoundException e) {
        e.printStackTrace();
        headers.setLocation(URI.create("/login"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 카카오 로그인을 이용 할 수 없습니다.");
      } 
      
      //접근토큰 통해 고객 정보 얻기
      KakaoUserInfoResponseDto kakaoUserInfoResponseDto = new KakaoUserInfoResponseDto();
      try {
        kakaoUserInfoResponseDto = kakaoOAuthLoginService.getKakaoUserByToken(oAuthToken);
      } catch (IOException | NotFoundException e) {
        e.printStackTrace();
        headers.setLocation(URI.create("/login"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 카카오 로그인을 이용 할 수 없습니다.");
      }
       
      //고객 정보통해 Dao 처리 및 로그인 처리
      // 계정 고유 id
      String oAuthid = kakaoUserInfoResponseDto.getId()+"";
      
      HttpSession session = req.getSession();
      try {
        // 계정 고유 id통해 oauth테이블에 데이터 있는지 확인 -> 없으면 NotFoundException 발생
        OAuthDto oAuthDto = userServices.getOAuthByOAuthId("KAKAO", oAuthid);
          if(oAuthDto.getUserSeq()!=0) {// 데이터 존재 (기존 회원 계정과 소셜 계정이 연동된 계정)
          // 해당 데이터의 userSeq 추출 및 user 테이블 데이터 읽기
          UserDto userDto = userServices.getUserBySeq(oAuthDto.getUserSeq()); // 여기서도 NotFoundException 발생 가능성이 있긴한데 여기서 발생시 로직에 문제가 있는거임.
          // 세션에 user 등록
          session.setAttribute("user", userDto);
          }
        // 세션에 토큰,OAuthDto 등록 (기존 회원에 연동된 계정이든 소셜 전용 계정이든 세션에 토큰 등록)
        session.setAttribute("oAuthDto", oAuthDto);
        session.setAttribute("oAuthToken", oAuthToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in()) * 1000));
        
        scheduleTokenRefresh("KAKAO",session, oAuthToken);

        headers.setLocation(URI.create("/home"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("로그인 성공");
      }
      
      catch (NotFoundException e) { //OAuth 처음 이용자 / oauth 테이블에 데이터 없음 등록 절차 진행
        try {
          System.out.println("OAuth 처음 이용자");
          userServices.registerOAuth("KAKAO", new OAuthDto(kakaoUserInfoResponseDto));
          session.setAttribute("oAuthToken", oAuthToken);
          session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in()) * 1000));
          headers.setLocation(URI.create("/home"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("회원가입 성공");
        } catch (SQLException | NotFoundException e1) {
          e1.printStackTrace();
          headers.setLocation(URI.create("/login"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
        }
      }
       catch (SQLException e) {
        e.printStackTrace();
        headers.setLocation(URI.create("/login"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");

      }  
      
    }
    
    @GetMapping("/refresh/kakao")
    public ResponseEntity<String> kakaoRefreshToken(HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain; charset=UTF-8");
      
//        HttpSession session = request.getSession(false);
        if (session == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("세션이 없습니다.");
        }
        OAuthDto oAuthDto = (OAuthDto)session.getAttribute("oAuthDto");
        if (oAuthDto == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("인증정보가 없습니다.");
        }
        OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
        if (oAuthToken == null) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰이 없습니다.");
        }
        Long oAuthTokenExpiry = (Long) session.getAttribute("oAuthTokenExpiry");
        if (oAuthTokenExpiry == null || System.currentTimeMillis() > oAuthTokenExpiry) {
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰 유효기간이 지났습니다.");
        }
        
        // 리프레시 토큰으로 새로운 엑세스 토큰 요청
        OAuthToken  newToken = null;
        try {
          String responseToken = kakaoOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
          ObjectMapper mapper = new ObjectMapper();
          newToken = mapper.readValue(responseToken, OAuthToken.class);
        } catch (URISyntaxException | IOException e) {
          e.printStackTrace();
          headers.setLocation(URI.create("/logout"));
          return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("토큰 갱신 실패");
        }

        // 새로운 토큰을 세션에 저장
        session.setAttribute("oAuthToken", newToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(newToken.getExpires_in())  * 1000));
        System.out.println("갱신 완료");
        return ResponseEntity.ok("토큰 갱신 성공");
    }
    
}
