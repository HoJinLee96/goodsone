package dtoNaverLogin;

public class OAuthToken {
  
  //공용
  private String access_token;
  private String token_type;
  private String expires_in;

  //네이버 공용
  private String error;
  private String error_description;
  
  //네이버 발급
  private String refresh_token;
    
  //네이버 삭제
  private String result;
  
  
  //카카오 공용
  private int refresh_token_expires_in;
  private String id_token;
  
  public OAuthToken() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  //네이버
  public OAuthToken(String access_token, String refresh_token, String token_type, String expires_in,
      String error, String error_description) {
    super();
    this.access_token = access_token;
    this.refresh_token = refresh_token;
    this.token_type = token_type;
    this.expires_in = expires_in;
    this.error = error;
    this.error_description = error_description;
  }
  
  
  public String getAccess_token() {
    return access_token;
  }
  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }
  public String getRefresh_token() {
    return refresh_token;
  }
  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }
  public String getToken_type() {
    return token_type;
  }
  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }
  public String getExpires_in() {
    return expires_in;
  }
  public void setExpires_in(String expires_in) {
    this.expires_in = expires_in;
  }
  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }
  public String getError_description() {
    return error_description;
  }
  public void setError_description(String error_description) {
    this.error_description = error_description;
  }

  public String getResult() {
    return result;
  }

  public int getRefresh_token_expires_in() {
    return refresh_token_expires_in;
  }

  public String getId_token() {
    return id_token;
  }

  
  
}
