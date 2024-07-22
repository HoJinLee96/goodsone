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
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import dto.NaverCallback;
import dto.NaverToken;

@Service
@PropertySource("classpath:application.properties")
public class OAuthLoginService {

  @Value("${naver-login.baseUrl}")
  private String baseUrl;
  @Value("${naver-login.clientId}")
  private String clientId;
  @Value("${naver-login.redirectUrl}")
  private String redirectUrl;
  @Value("${naver-login.clientSecret}")
  private String clientSecret;
  
  @PostConstruct
  private void init() {
      System.out.println("naver-login.baseUrl : " + baseUrl);
      System.out.println("naver-login.clientId : " + clientId);
      System.out.println("naver-login.redirectUrl : " + redirectUrl);
      System.out.println("naver-login.clientSecret : " + clientSecret);
  }
  
  public String getNaverAuthorizeUrl(String type) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

      UriComponents uriComponents = UriComponentsBuilder
              .fromUriString(baseUrl + "/" + type)
              .queryParam("response_type", "code")
              .queryParam("client_id", clientId)
              .queryParam("redirect_uri", URLEncoder.encode(redirectUrl, "UTF-8"))
              .queryParam("state", URLEncoder.encode("1234", "UTF-8"))
              .build();

      return uriComponents.toString();
  }
  
  public String getNaverTokenUrl(String type, NaverCallback callback) throws URISyntaxException, IOException {

    UriComponents uriComponents = UriComponentsBuilder
            .fromUriString(baseUrl + "/" + type)
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("code", callback.getCode())
            .queryParam("state", URLEncoder.encode(callback.getState(), "UTF-8"))
            .build();

        URL url = new URL(uriComponents.toString());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

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
  
  public String getNaverUserByToken(NaverToken token) throws IOException {

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
