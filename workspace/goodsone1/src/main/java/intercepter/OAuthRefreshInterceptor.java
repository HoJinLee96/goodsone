package intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.OAuthDto;
import dtoNaverLogin.OAuthToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.KakaoOAuthLoginService;
import service.NaverOAuthLoginService;

@Component
public class OAuthRefreshInterceptor implements HandlerInterceptor {

  KakaoOAuthLoginService kakaoOAuthLoginService;
  NaverOAuthLoginService naverOAuthLoginService;
  
  @Autowired
  public OAuthRefreshInterceptor(KakaoOAuthLoginService kakaoOAuthLoginService,
      NaverOAuthLoginService naverOAuthLoginService) {
    super();
    this.kakaoOAuthLoginService = kakaoOAuthLoginService;
    this.naverOAuthLoginService = naverOAuthLoginService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    System.out.println("OAuthRefreshInterceptor.preHandle() 실행");
    HttpSession session = request.getSession();

    if (session == null) {
      return true;
    }
    OAuthDto oAuthDto = (OAuthDto)session.getAttribute("oAuthDto");
    if (oAuthDto == null) {
      return true;
    }
    OAuthToken oAuthToken = (OAuthToken) session.getAttribute("oAuthToken");
    if (oAuthToken == null) {
      return true;
    }
    Long oAuthTokenExpiry = (Long) session.getAttribute("oAuthTokenExpiry");
    if (oAuthTokenExpiry == null || System.currentTimeMillis() > oAuthTokenExpiry) {
      response.sendRedirect("/logout");
      return false;
    }
    
    System.out.println("세션에 저장된 토큰 남은 시간 = "+(oAuthTokenExpiry - System.currentTimeMillis()));
    if ((oAuthTokenExpiry - System.currentTimeMillis()) <= 5 * 60 * 1000) {
      
    String provider = oAuthDto.getProvider();
    ObjectMapper mapper = new ObjectMapper();
    if (provider.equals("NAVER")) {
      String responseToken = naverOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
      oAuthToken = mapper.readValue(responseToken, OAuthToken.class);
      System.out.println("네이버 갱신 완료");
    }
    else if (provider.equals("KAKAO")) {
      String responseToken = kakaoOAuthLoginService.updateTokenUrl("token","refresh_token",oAuthToken);
      JsonNode rootNode = mapper.readTree(responseToken);
      String access_token = rootNode.get("access_token").asText();
      String id_token = rootNode.get("id_token").asText();
      oAuthToken.setAccess_token(access_token);
      oAuthToken.setId_token(id_token);
      System.out.println("카카오 갱신 완료");

    }
    session.setAttribute("oAuthToken", oAuthToken);
    session.setAttribute("oAuthTokenExpiry", System.currentTimeMillis() + (Integer.parseInt(oAuthToken.getExpires_in())  * 1000));
    
    return true;
    
    }
    return true;
  }

}
