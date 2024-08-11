package api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OAuthDto;
import dto.UserDto;
import dtoNaverLogin.Callback;
import dtoNaverLogin.NaverRes;
import dtoNaverLogin.OAuthToken;
import exception.NotFoundException;
import service.NaverOAuthLoginService;
import service.OAuthService;
import service.UserServices;

@RestController
@RequestMapping("/naver")
public class NaverLoginController {
  
  private UserServices userServices;
  private OAuthService oAuthService;
  private NaverOAuthLoginService naverOAuthLoginService;
  
  @Autowired
  public NaverLoginController(UserServices userServices, OAuthService oAuthService,
      NaverOAuthLoginService naverOAuthLoginService) {
    super();
    this.userServices = userServices;
    this.oAuthService = oAuthService;
    this.naverOAuthLoginService = naverOAuthLoginService;
  }
  
  @GetMapping("/login/url")
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
  
  @GetMapping("/login/callback")
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
        OAuthDto oAuthDto = oAuthService.getOAuthByOAuthId("NAVER", oAuthid);
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
        
        headers.setLocation(URI.create("/home"));
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("로그인 성공");
      }
      
      catch (NotFoundException e) { //OAuth 처음 이용자 / oauth 테이블에 데이터 없음 등록 절차 진행
        try {
          System.out.println("OAuth 처음 이용자");
          oAuthService.registerOAuth("NAVER", new OAuthDto(naverUser));
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
  
  // 토큰 갱신
  @GetMapping("/token/refresh")
  public ResponseEntity<String> naverTokenRefresh(HttpSession session) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "text/plain; charset=UTF-8");
    
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

  
  
  // 회원 탈퇴
  @GetMapping("/token/delete")
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
          oAuthService.stopOAuthDtoByOAuthId(oAuthDto.getId());
          session.invalidate();
//          session.removeAttribute("oAuthDto");
//          session.removeAttribute("oAuthToken");
//          session.removeAttribute("oAuthTokenExpiry");
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
}