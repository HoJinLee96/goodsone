package api;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.KakaoUserInfoResponseDto;
import dto.OAuthDto;
import dto.UserDto;
import dtoNaverLogin.OAuthToken;
import exception.NotFoundException;
import service.KakaoOAuthLoginService;
import service.OAuthService;
import service.UserServices;

@RestController
@RequestMapping("/kakao")
public class KakaoLoginController {
  
  private UserServices userServices;
  private OAuthService oAuthService;
  private KakaoOAuthLoginService kakaoOAuthLoginService;
  
  @Autowired
  public KakaoLoginController(UserServices userServices, OAuthService oAuthService,
      KakaoOAuthLoginService kakaoOAuthLoginService) {
    super();
    this.userServices = userServices;
    this.oAuthService = oAuthService;
    this.kakaoOAuthLoginService = kakaoOAuthLoginService;
  }
  
  @GetMapping("/login/url")
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
  


  @GetMapping("/login/callback")
  public ResponseEntity<String> loginKakaoCallBack(HttpServletRequest req, HttpServletResponse res,@RequestParam(value = "code", defaultValue = "defaultCode") String code,
      @RequestParam(value = "error", defaultValue = "defaultValue") String error) {
    /**
    1. 결과 확인
    2. 인가 코드 통해 접근 토큰 얻기
    3. 접근토큰 통해 고객 정보 얻기
    4. user 테이블에서 같인 이메일로 가입된 계정이 있는지 확인.
    4. oauth 테이블에서 데이터 확인
    4-1. 처음 이용자 처리
    4-2. 기존 이용자
    4-2-1. 기존 계정과 연동된 sns 계정 이용자 처리
    4-2-2. sns 전용 계정 이용자 처리
    **/
    System.out.println("LoginController.loginKakaoCallBack() 실행");
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    
//    1. 결과 확인
    if (!error.equals("defaultValue")) {
      return handleError(headers, HttpStatus.INTERNAL_SERVER_ERROR, error);
    }
  
//    2. 인가 코드 통해 접근 토큰 얻기
    ObjectMapper mapper = new ObjectMapper();
    OAuthToken oAuthToken = new OAuthToken();
    String responseBody = "";
    try {
      responseBody = kakaoOAuthLoginService.getKakaoToken("token",code);
      oAuthToken = mapper.readValue(responseBody, OAuthToken.class);
      System.out.println(oAuthToken.toString());
      
    } catch (JsonProcessingException | NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 카카오 로그인을 이용 할 수 없습니다.");
    } 
    
//    3. 접근토큰 통해 고객 정보 얻기
    KakaoUserInfoResponseDto kakaoUserInfoResponseDto = new KakaoUserInfoResponseDto();
    try {
      kakaoUserInfoResponseDto = kakaoOAuthLoginService.getKakaoUserByToken(oAuthToken);
    } catch (IOException | NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 카카오 로그인을 이용 할 수 없습니다.");
    }

    HttpSession session = req.getSession();
//    4. 고객 정보의 이메일을 이용해 기존 계정에 가입된 적 있는지 확인하여 안내하기
    try {
      String confirmEmail = kakaoUserInfoResponseDto.getKakao_account().getEmail();
      UserDto confirmUserDto = userServices.getUserByEmail(confirmEmail);
      session.setAttribute("confirmUserDto", confirmUserDto);
      return ResponseEntity.status(HttpStatus.MULTIPLE_CHOICES).headers(headers).body("기존 회원 중 같은 이메일로 가입된 계정이 존재.");
    } catch (NotFoundException e2) {
      
//    5. oauth테이블에서 데이터 확인
    // 계정 고유 id
    String oAuthid = kakaoUserInfoResponseDto.getId()+"";
    
    
    OAuthDto oAuthDto = new OAuthDto(kakaoUserInfoResponseDto);
    try {
      // 계정 고유 id통해 oauth테이블에 데이터 있는지 확인 -> 없으면 NotFoundException 발생
      oAuthDto = oAuthService.getOAuthByOAuthId("KAKAO", oAuthid);
    } 
//    5-1. 처음 이용자
//    oauth 테이블에 데이터 없음 등록 절차 진행
    catch (NotFoundException e) {
      try {
        System.out.println("OAuth 처음 이용자");
        int userSeq = oAuthService.registerOAuth("KAKAO", oAuthDto);
        oAuthDto.setUserSeq(userSeq);
        session.setAttribute("oAuthDto", oAuthDto);
        session.setAttribute("oAuthToken", oAuthToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in()) * 1000));
        headers.setLocation(URI.create("/home"));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("회원가입 성공");
      } catch (SQLException e1) {
        e1.printStackTrace();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
      headers.setLocation(URI.create("/login"));
      return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("현재 네이버 로그인을 이용 할 수 없습니다.");
    }
    
    try {
      if(oAuthDto.getUserSeq()!=0) {// 데이터 존재 (기존 회원 계정과 소셜 계정이 연동된 계정)
        // 해당 데이터의 userSeq 추출 및 user 테이블 데이터 읽기
        UserDto userDto = userServices.getUserBySeq(oAuthDto.getUserSeq()); // 여기서 NotFoundException 발생 가능성이 있긴한데 여기서 발생시 로직에 문제가 있는거임.
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
    catch (NotFoundException e) {
      // 발생되면 안되는 익셉션
      // oauth 테이블 데이터에는 userSeq가 등록되어 있고, user 테이블에는 해당 userSeq없는 상황.
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("db 데이터 불일치.");
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("");
    }
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("");
    }
    
  }
  
  //토큰 갱신
  @GetMapping("/token/refresh")
  public ResponseEntity<String> kakaoTokenRefresh(HttpServletRequest req) {
    System.out.println("LoginController.kakaoRefreshToken() 실행");
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "text/plain; charset=UTF-8");
    
      HttpSession session = req.getSession(false);
      ResponseEntity<String> sessionConfirmResult = sessionConfirm(session,headers);
      if(sessionConfirmResult.getStatusCode() != HttpStatus.OK)
        return sessionConfirmResult;
      
      OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
      
      // 리프레시 토큰으로 새로운 엑세스 토큰 요청
      try {
        String responseToken = kakaoOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseToken);
        String access_token = rootNode.get("access_token").asText();
        String id_token = rootNode.get("id_token").asText();
        oAuthToken.setAccess_token(access_token);
        oAuthToken.setId_token(id_token);
        
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("소셜 로그인 연장 실패");
      }

      // 새로운 토큰을 세션에 저장
      session.setAttribute("oAuthToken", oAuthToken);
      session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in())  * 1000));
      System.out.println("갱신 완료");
      return ResponseEntity.ok("소셜 로그인 연장 성공");
  }
  
  //회원 탈퇴
  @GetMapping("/token/delete")
  public ResponseEntity<String> kakaoDeleteToken(HttpServletRequest request) {
    //1. 세션에 저장되어있는 OAuthDto,OAuthToken 가지고 회원 탈퇴 진행.
    //2. OAuthToken가 만료기간때문에 갱신을 먼저 진행
    //3. 갱신한 OAuthToken가지고 회원 정보 얻기
    //4. 얻은 회원 정보와, OAuthDto 일치한지 확인(고유 id값)
    //5. DB 회원 탈퇴 적용(stats 값 stop으로 변경)
    //6. 카카오 서버 회원 탈퇴 진행
    
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
      try {
        String responseToken = kakaoOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseToken);
        String access_token = rootNode.get("access_token").asText();
        String id_token = rootNode.get("id_token").asText();
        oAuthToken.setAccess_token(access_token);
        oAuthToken.setId_token(id_token);
        session.setAttribute("oAuthToken", oAuthToken);
        session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in())  * 1000));
      } catch (URISyntaxException | IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }
      System.out.println("카카오 회원 탈퇴 2단계 성공");
      
      
      //3. 갱신한 OAuthToken가지고 회원 정보 얻기
      KakaoUserInfoResponseDto kakaoUserInfoResponseDto = new KakaoUserInfoResponseDto();
      try {
        kakaoUserInfoResponseDto = kakaoOAuthLoginService.getKakaoUserByToken(oAuthToken);
      } catch (IOException | NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 탈퇴가 불가능합니다. 잠시후 시도 부탁드립니다.");
      }
      System.out.println("카카오 회원 탈퇴 3단계 성공");

      
      //4. 얻은 회원 정보와, OAuthDto 일치한지 확인(고유 id값)
      if(!(oAuthDto.getId().equals(kakaoUserInfoResponseDto.getId()+""))) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body("재로그인 후 탈퇴 이용 부탁드립니다.");
      }
      System.out.println("카카오 회원 탈퇴 4단계 성공");
      
      
      //5. DB 회원 탈퇴 적용(stats 값 stop으로 변경)
      try {
        oAuthService.stopOAuthDtoByOAuthId(oAuthDto.getId());
        session.invalidate();
        System.out.println("카카오 회원 탈퇴 5단계 성공");
      } catch (SQLException | NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 회원탈퇴 할 수 없는 계정입니다. \n 1:1 문의 부탁드립니다.");
      }
      
      //6. 카카오 서버 회원 탈퇴 진행
      String deleteId = "";
      try {
        deleteId = kakaoOAuthLoginService.stopKakaoUserBy(oAuthToken);
        System.out.println("카카오톡 서버 회원탈퇴 완료 deleteId = " + deleteId);
        System.out.println("카카오 회원 탈퇴 최종 성공");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("현재 탈퇴 완료.");
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
  
  private ResponseEntity<String> handleError(HttpHeaders headers, HttpStatus status, String message) {
    return ResponseEntity.status(status).headers(headers).body(message);
  }

  private ResponseEntity<String> handleSuccess(HttpHeaders headers, HttpStatus status, String message) {
    return ResponseEntity.status(status).headers(headers).body(message);
  }
  
}
