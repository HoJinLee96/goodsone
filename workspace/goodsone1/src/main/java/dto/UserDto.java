package dto;
import java.time.LocalDateTime;

public class UserDto {
      private int userSeq;
      private String email;
      private String password;
      private String oldPassword;
      private String name;
      private String nickname;
      private String birth;
      private String mobileCarrier;
      private String phone;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;
      private String userStatus;
      private boolean marketingReceivedStatus;
      private int tierSeq;
      
      //회원가입생성자
      public UserDto(String email, String password, String name, String birth, String mobileCarrier, String phone,
          boolean marketingReceivedStatus) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.mobileCarrier = mobileCarrier;
        this.phone = phone;
        this.marketingReceivedStatus = marketingReceivedStatus;
      }

      //전체생성자
      public UserDto(int userSeq, String email, String password, String oldPassword, String name,
          String nickname, String birth, String mobileCarrier, String phone,
          LocalDateTime createdAt, LocalDateTime updatedAt, String userStatus,
          boolean marketingReceivedStatus, int tierSeq) {
        super();
        this.userSeq = userSeq;
        this.email = email;
        this.password = password;
        this.oldPassword = oldPassword;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.mobileCarrier = mobileCarrier;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userStatus = userStatus;
        this.marketingReceivedStatus = marketingReceivedStatus;
        this.tierSeq = tierSeq;
      }

      //Getter
      
      public int getUserSeq() {
        return userSeq;
      }

      public String getEmail() {
        return email;
      }

      public String getPassword() {
        return password;
      }

      public String getOldPassword() {
        return oldPassword;
      }

      public String getName() {
        return name;
      }

      public String getNickname() {
        return nickname;
      }

      public String getBirth() {
        return birth;
      }

      public String getMobileCarrier() {
        return mobileCarrier;
      }

      public String getPhone() {
        return phone;
      }

      public LocalDateTime getCreatedAt() {
        return createdAt;
      }

      public LocalDateTime getUpdatedAt() {
        return updatedAt;
      }

      public String getUserStatus() {
        return userStatus;
      }

      public boolean isMarketingReceivedStatus() {
        return marketingReceivedStatus;
      }

      public int getTierSeq() {
        return tierSeq;
      }

      
      
      
      // Setter
      
    
}