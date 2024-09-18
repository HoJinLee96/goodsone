package dto;
import java.time.LocalDateTime;

public class UserDto extends User {
      private int userSeq;
      private String email;
      private String password;
      private String name;
      private String birth;
      private String mobileCarrier;
      private String phone;
      private Status status;
      private boolean marketingReceivedStatus;
      private LocalDateTime createdAt;
      private LocalDateTime updatedAt;
      
      
      public UserDto() {}

      //회원가입생성자
      public UserDto(String email, String password, String name, String birth, String phone,
          boolean marketingReceivedStatus) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.marketingReceivedStatus = marketingReceivedStatus;
      }

      public UserDto(int userSeq, String email, String name, String birth, String phone,
          Status status, boolean marketingReceivedStatus, LocalDateTime createdAt,
          LocalDateTime updatedAt) {
        super();
        this.userSeq = userSeq;
        this.email = email;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
        this.status = status;
        this.marketingReceivedStatus = marketingReceivedStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
      }

      @Override
      public String getProvider() {
        return "PUBLIC";
      }

      public int getUserSeq() {
        return userSeq;
      }

      public String getEmail() {
        return email;
      }

      public String getPassword() {
        return password;
      }

      public String getName() {
        return name;
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

      public Status getStatus() {
        return status;
      }

      public boolean isMarketingReceivedStatus() {
        return marketingReceivedStatus;
      }

      public LocalDateTime getCreatedAt() {
        return createdAt;
      }

      public LocalDateTime getUpdatedAt() {
        return updatedAt;
      }

      
      // setter
      
      public void setStatus(Status status) {
        this.status = status;
      }
      
      
      
      
      
    
}