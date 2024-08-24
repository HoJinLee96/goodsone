package dto;
import java.time.LocalDateTime;

public class UserDto {
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
      
      public enum Status {
        NORMAL, STAY, STOP
      }
      
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
      
      
      // Builder 패턴
      private UserDto(Builder builder) {
          this.userSeq = builder.userSeq;
          this.email = builder.email;
          this.password = builder.password;
          this.name = builder.name;
          this.birth = builder.birth;
          this.mobileCarrier = builder.mobileCarrier;
          this.phone = builder.phone;
          this.createdAt = builder.createdAt;
          this.updatedAt = builder.updatedAt;
          this.status = builder.status;
          this.marketingReceivedStatus = builder.marketingReceivedStatus;
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

      public LocalDateTime getCreatedAt() {
        return createdAt;
      }

      public LocalDateTime getUpdatedAt() {
        return updatedAt;
      }

      public Status getStatus() {
        return status;
      }

      public boolean isMarketingReceivedStatus() {
        return marketingReceivedStatus;
      }


      public static class Builder {
        private int userSeq;
        private String email;
        private String password;
        private String name;
        private String birth;
        private String mobileCarrier;
        private String phone;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Status status;  // 변경된 부분: String에서 Status로 수정
        private boolean marketingReceivedStatus;

        public Builder userSeq(int userSeq) {
            this.userSeq = userSeq;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
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

        public Builder mobileCarrier(String mobileCarrier) {
            this.mobileCarrier = mobileCarrier;
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

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder status(Status status) {  // 변경된 부분: String에서 Status로 수정
          this.status = status;
          return this;
        }

        public Builder marketingReceivedStatus(boolean marketingReceivedStatus) {
            this.marketingReceivedStatus = marketingReceivedStatus;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
      
      
      // Setter
      
    
}