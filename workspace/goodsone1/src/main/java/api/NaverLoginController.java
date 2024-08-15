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
        } catch (SQLException e1) {
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
  public ResponseEntity<String> naverTokenRefresh(HttpServletRequest request) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "text/plain; charset=UTF-8");
      HttpSession session = request.getSession(false);
    
      ResponseEntity<String> sessionConfirmResult = sessionConfirm(session,headers);
      if(sessionConfirmResult.getStatusCode() != HttpStatus.OK)
        return sessionConfirmResult;
      
      OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
      
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
      
      //1. 세션에 저장되어있는 OAuthDto,OAuthToken 가지고 회원 탈퇴 진행.
      ResponseEntity<String> sessionConfirmResult = sessionConfirm(session,headers);
      if(sessionConfirmResult.getStatusCode() != HttpStatus.OK)
        return sessionConfirmResult;
      
      OAuthDto oAuthDto = (OAuthDto) session.getAttribute("oAuthDto");
      OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
      
      System.out.println("카카오 회원 탈퇴 1단계 성공");

      
      //2. OAuthToken가 만료기간때문에 갱신을 먼저 진행
      OAuthToken  newToken = null;
      try {
        String responseToken = naverOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
        ObjectMapper mapper = new ObjectMapper();
        newToken = mapper.readValue(responseToken, OAuthToken.class);
        session.setAttribute("oAuthToken", newToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(newToken.getExpires_in())  * 1000));
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }
      System.out.println("네이버 회원 탈퇴 2단계 성공");

    
      //3. 갱신한 OAuthToken가지고 회원 정보 얻기
      NaverRes naverUser = null;
      try {
        String responseUser = naverOAuthLoginService.getNaverUserByToken(newToken);
        ObjectMapper mapper = new ObjectMapper();
        naverUser = mapper.readValue(responseUser, NaverRes.class);
      } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }
      System.out.println("네이버 회원 탈퇴 3단계 성공");
      
      
      //4. 얻은 회원 정보와, OAuthDto 일치한지 확인(고유 id값)
      if(!(oAuthDto.getId().equals(naverUser.getResponse().getId()))) {
        session.invalidate();
        System.out.println("얻은 회원 정보와, OAuthDto 일치한지 확인(고유 id값) : 일치하지 않음.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("재로그인 후 탈퇴 이용 부탁드립니다.");
      }
      System.out.println("네이버 회원 탈퇴 4단계 성공");

      
      //5. DB 회원 탈퇴 적용(stats 값 stop으로 변경)
      try {
        oAuthService.stopOAuthDtoByOAuthId(oAuthDto.getId());
        session.invalidate();
        System.out.println("카카오 회원 탈퇴 4단계 성공");
      } catch (SQLException | NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }

      //6. 네이버 서버 회원 탈퇴 진행
      OAuthToken deleteToken =null;
      try {
        String responseToken = naverOAuthLoginService.updateTokenUrl("token","delete",newToken);
        ObjectMapper mapper = new ObjectMapper();
        deleteToken = mapper.readValue(responseToken, OAuthToken.class);
        String result = deleteToken.getResult();
        if(result.equals("success")) {
          return ResponseEntity.status(HttpStatus.OK).headers(headers).body("현재 탈퇴 완료.");
        } else {
          System.out.println(deleteToken.getError() + deleteToken.getError_description());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
        }
      } catch (IOException | URISyntaxException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }
      

      
  }
  
  private ResponseEntity<String> sessionConfirm(HttpSession session,HttpHeaders headers) {
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
    return ResponseEntity.ok("성공");
  }
}
