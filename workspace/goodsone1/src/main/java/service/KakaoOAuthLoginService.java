package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtoNaverLogin.OAuthToken;
import exception.NotFoundException;

@Service
@PropertySource("classpath:application.properties")
public class KakaoOAuthLoginService {
  @Value("${kakao-login.baseUrl}")
  private String baseUrl;
  @Value("${kakao-login.clientId}")
  private String clientId;
  @Value("${kakao-login.redirectUrl}")
  private String redirectUrl;
  @Value("${kakao-login.clientSecret}")
  private String clientSecret;
  @Value("${kakao-login.redirectUri}")
  private String redirectUri;
  
  @PostConstruct
  private void init() {
      System.out.println("kakao-login.baseUrl : " + baseUrl);
      System.out.println("kakao-login.clientId : " + clientId);
      System.out.println("kakao-login.redirectUrl : " + redirectUrl);
      System.out.println("kakao-login.clientSecret : " + clientSecret);
      System.out.println("kakao-login.redirectUri : " + redirectUri);
  }
  
  
  public String getKakaoAuthorizeUrl(String path) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {
System.out.println("KakaoOAuthLoginService.getKakaoAuthorizeUrl()");
    UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(baseUrl + "/" + path)
            .queryParam("client_id", clientId)
            .queryParam("redirect_uri", URLEncoder.encode(redirectUrl, "UTF-8"))
            .queryParam("response_type", "code")
//            .queryParam("state", URLEncoder.encode("1234", "UTF-8"))
            .build();

    return uriComponents.toString();
}

public String getKakaoTokenUrl(String path, String code) throws NotFoundException {
  HttpHeaders headers = new HttpHeaders();
  headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
  
  UriComponents uriComponents = UriComponentsBuilder
          .fromUriString(baseUrl + "/" + path)
          .queryParam("grant_type", "authorization_code")
          .queryParam("client_id", clientId)
          .queryParam("redirect_uri",redirectUri )
          .queryParam("code", code)
          .build();

      RestTemplate restTemplate = new RestTemplate();
      HttpEntity<String> entity = new HttpEntity<>(headers);

      ResponseEntity<String> response = restTemplate.exchange(
          uriComponents.toUriString(), 
          HttpMethod.GET, 
          entity, 
          String.class
  );
      
      int responseCode = response.getStatusCodeValue();
      String responseBody = response.getBody();
      
      System.out.println("responseCode = " + responseCode);
      if(responseCode==200) {
        return responseBody;
      }else {
        throw new NotFoundException();
      }
}

public String getKakaoUserByToken(OAuthToken token) {

  String accessToken = token.getAccess_token();
  String tokenType = token.getToken_type();

  URL url = new URL("https://openapi.naver.com/v1/nid/me");
  HttpURLConnection con = (HttpURLConnection)url.openConnection();
  con.setRequestMethod("GET");
  con.setRequestProperty("Authorization", tokenType + " " + accessToken);

  int responseCode = con.getResponseCode();
  BufferedReader br;

  if(responseCode==200) { // 정상 호출
      br = new BufferedReader(new InputStreamReader(con.getInputStream()));
  } else {  // 에러 발생
      br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
  }

  String inputLine;
  StringBuffer response = new StringBuffer();
  while ((inputLine = br.readLine()) != null) {
      response.append(inputLine);
  }

  br.close();
  return response.toString();
}
  
}
