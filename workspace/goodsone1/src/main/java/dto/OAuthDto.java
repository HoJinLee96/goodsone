package dto;

import java.time.LocalDateTime;
import dtoNaverLogin.NaverRes;

public class OAuthDto {
  private int oauthSeq;
  private int userSeq;
  private String provider; // "NAVER" 또는 "KAKAO"
  private String id;
  private String email;
  private String name;
  private String birth;
  private String phone;
  private LocalDateTime createdAt;
  
  private OAuthDto() {}
  
  //생성자
  public OAuthDto(String provider, String id, String email, String name, String birth,
      String phone, LocalDateTime createdAt) {
    super();
    this.provider = provider;
    this.id = id;
    this.email = email;
    this.name = name;
    this.birth = birth;
    this.phone = phone;
    this.createdAt = createdAt;
  }

  //전체생성자
  public OAuthDto(int oauthSeq, int userSeq, String provider, String id, String email, String name,
      String birth, String phone, LocalDateTime createdAt) {
    super();
    this.oauthSeq = oauthSeq;
    this.userSeq = userSeq;
    this.provider = provider;
    this.id = id;
    this.email = email;
    this.name = name;
    this.birth = birth;
    this.phone = phone;
    this.createdAt = createdAt;
  }
  
  public OAuthDto(NaverRes naverUser) {
    this.id = naverUser.getResponse().getId();
    this.email = naverUser.getResponse().getEmail();
    this.name = naverUser.getResponse().getName();
    this.birth = naverUser.getResponse().getBirthyear()+naverUser.getResponse().getBirthday();
    this.phone = naverUser.getResponse().getMobile();
  }
  
  public static class Builder{
    
    private int oauthSeq;
    private int userSeq;
    private String provider; // "NAVER" 또는 "KAKAO"
    private String id;
    private String email;
    private String name;
    private String birth;
    private String phone;
    private LocalDateTime createdAt;
    
    public Builder() {}

    public Builder oauthSeq(int oauthSeq) {
        this.oauthSeq = oauthSeq;
        return this;
    }

    public Builder userSeq(int userSeq) {
        this.userSeq = userSeq;
        return this;
    }

    public Builder provider(String provider) {
        this.provider = provider;
        return this;
    }

    public Builder id(String id) {
        this.id = id;
        return this;
    }

    public Builder email(String email) {
        this.email = email;
        return this;
    }

    public Builder name(String name) {
        this.name = name;
        return this;
    }

    public Builder birth(String birth) {
        this.birth = birth;
        return this;
    }

    public Builder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    // build 메서드: OAuthDto 객체 생성
    public OAuthDto build() {
        OAuthDto oAuthDto = new OAuthDto();
        oAuthDto.oauthSeq = this.oauthSeq;
        oAuthDto.userSeq = this.userSeq;
        oAuthDto.provider = this.provider;
        oAuthDto.id = this.id;
        oAuthDto.email = this.email;
        oAuthDto.name = this.name;
        oAuthDto.birth = this.birth;
        oAuthDto.phone = this.phone;
        oAuthDto.createdAt = this.createdAt;
        return oAuthDto;
    }
}

  public int getOauthSeq() {
    return oauthSeq;
  }
  public int getUserSeq() {
    return userSeq;
  }
  public String getProvider() {
    return provider;
  }
  public String getId() {
    return id;
  }
  public String getEmail() {
    return email;
  }
  public String getName() {
    return name;
  }
  public String getBirth() {
    return birth;
  }
  public String getPhone() {
    return phone;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  
  
  
  
  
  
}
