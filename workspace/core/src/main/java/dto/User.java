package dto;
import java.time.LocalDateTime;

public class User {
    private final int userSeq;
    private final String userId;
    private final String userPassword;
    private final String userOldPassword;
    private final String userName;
    private final String userNickname;
    private final String userBirth;
    private final String userPhoneAgency;
    private final String userPhoneNumber;
    private final String userEmail;
    private final String userAddress;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String userStatus;
    private final String userSignType;

    private User(Builder builder) {
        this.userSeq = builder.userSeq;
        this.userId = builder.userId;
        this.userPassword = builder.userPassword;
        this.userOldPassword = builder.userOldPassword;
        this.userName = builder.userName;
        this.userNickname = builder.userNickname;
        this.userBirth = builder.userBirth;
        this.userPhoneAgency = builder.userPhoneAgency;
        this.userPhoneNumber = builder.userPhoneNumber;
        this.userEmail = builder.userEmail;
        this.userAddress = builder.userAddress;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.userStatus = builder.userStatus;
        this.userSignType = builder.userSignType;
    }

    public int getUserSeq() {
        return userSeq;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserOldPassword() {
        return userOldPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public String getUserPhoneAgency() {
        return userPhoneAgency;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
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

    public String getUserSignType() {
        return userSignType;
    }

    public static class Builder {
        private int userSeq;
        private String userId;
        private String userPassword;
        private String userOldPassword;
        private String userName;
        private String userNickname;
        private String userBirth;
        private String userPhoneAgency;
        private String userPhoneNumber;
        private String userEmail;
        private String userAddress;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String userStatus;
        private String userSignType;

        public Builder userSeq(int userSeq) {
            this.userSeq = userSeq;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userPassword(String userPassword) {
            this.userPassword = userPassword;
            return this;
        }

        public Builder userOldPassword(String userOldPassword) {
            this.userOldPassword = userOldPassword;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder userNickname(String userNickname) {
            this.userNickname = userNickname;
            return this;
        }

        public Builder userBirth(String userBirth) {
            this.userBirth = userBirth;
            return this;
        }

        public Builder userPhoneAgency(String userPhoneAgency) {
            this.userPhoneAgency = userPhoneAgency;
            return this;
        }

        public Builder userPhoneNumber(String userPhoneNumber) {
            this.userPhoneNumber = userPhoneNumber;
            return this;
        }

        public Builder userEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public Builder userAddress(String userAddress) {
            this.userAddress = userAddress;
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

        public Builder userStatus(String userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public Builder userSignType(String userSignType) {
            this.userSignType = userSignType;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}