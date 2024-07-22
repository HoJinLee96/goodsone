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
      private String status;
      private boolean marketingReceivedStatus;
      private String tierName;
      
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
      
      //전체생성자
      public UserDto(int userSeq, String email, String password, String oldPassword, String name,
          String nickname, String birth, String mobileCarrier, String phone,
          LocalDateTime createdAt, LocalDateTime updatedAt, String status,
          boolean marketingReceivedStatus, String tierName) {
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
        this.status = status;
        this.marketingReceivedStatus = marketingReceivedStatus;
        this.tierName = tierName;
      }
      
      // Builde
      private UserDto(Builder builder) {
        this.userSeq = builder.userSeq;
        this.email = builder.email;
        this.password = builder.password;
        this.oldPassword = builder.oldPassword;
        this.name = builder.name;
        this.nickname = builder.nickname;
        this.birth = builder.birth;
        this.mobileCarrier = builder.mobileCarrier;
        this.phone = builder.phone;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.status = builder.status;
        this.marketingReceivedStatus = builder.marketingReceivedStatus;
        this.tierName = builder.tierName;
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

      public String getStatus() {
        return status;
      }

      public boolean isMarketingReceivedStatus() {
        return marketingReceivedStatus;
      }

      public String getTierName() {
        return tierName;
      }



    public static class Builder {
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
        private String status;
        private boolean marketingReceivedStatus;
        private String tierName;

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

        public Builder oldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
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

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder marketingReceivedStatus(boolean marketingReceivedStatus) {
            this.marketingReceivedStatus = marketingReceivedStatus;
            return this;
        }

        public Builder tierName(String tierName) {
            this.tierName = tierName;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
      
      
      // Setter
      
    
}