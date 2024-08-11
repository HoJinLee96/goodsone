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
import dtoNaverLogin.Callback;
import dtoNaverLogin.OAuthToken;

@Service
@PropertySource("classpath:application.properties")
public class NaverOAuthLoginService {

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
  
  public String getNaverAuthorizeUrl(String path) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException {

      UriComponents uriComponents = UriComponentsBuilder
              .fromUriString(baseUrl + "/" + path)
              .queryParam("response_type", "code")
              .queryParam("client_id", clientId)
              .queryParam("redirect_uri", URLEncoder.encode(redirectUrl, "UTF-8"))
              .build();

      return uriComponents.toString();
  }
  
  public String getTokenUrl(String path,Callback callback) throws URISyntaxException, IOException {

    UriComponents uriComponents =UriComponentsBuilder
            .fromUriString(baseUrl + "/" + path)
            .queryParam("grant_type", "authorization_code")
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("code", callback.getCode())
//            .queryParam("state", new URL().)
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
  public String updateTokenUrl(String path,String grant_type,OAuthToken oAuthToken) throws URISyntaxException, IOException {

        UriComponentsBuilder uriComponentsBuilder =UriComponentsBuilder
                .fromUriString(baseUrl + "/" + path)
                .queryParam("grant_type", grant_type)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret);
        
        if(grant_type.equals("refresh_token")) {
          uriComponentsBuilder.queryParam("refresh_token", oAuthToken.getRefresh_token());
        }else if(grant_type.equals("delete")){
          uriComponentsBuilder.queryParam("access_token", URLEncoder.encode(oAuthToken.getAccess_token(),"UTF-8"));
        }else {
          throw new IOException("grant_type 잘못 입력");
        }
        
        UriComponents uriComponents = uriComponentsBuilder.build();
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
  
  public String getNaverUserByToken(OAuthToken token) throws IOException {

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
