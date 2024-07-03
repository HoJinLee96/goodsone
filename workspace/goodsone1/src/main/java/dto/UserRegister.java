package dto;

import java.time.LocalDateTime;

public class UserRegister {

  private final int userSeq;
  private final String userEmail;
  private final String userPassword;
//  private final String userOldPassword;
  private final String userName;
//  private final String userNickname;
  private final String userBirth;
//  private final String userPhoneAgency;
  private final String userPhoneNumber;
  private final String userAddress;
  private final LocalDateTime createdAt;
//  private final LocalDateTime updatedAt;
//  private final String userStatus;
  private final String userSignType;
  
  public UserRegister(int userSeq, String userEmail, String userPassword, String userName,
      String userBirth, String userPhoneNumber, String userAddress, LocalDateTime createdAt,
      LocalDateTime updatedAt, String userStatus, String userSignType) {
    super();
    this.userSeq = userSeq;
    this.userEmail = userEmail;
    this.userPassword = userPassword;
    this.userName = userName;
    this.userBirth = userBirth;
    this.userPhoneNumber = userPhoneNumber;
    this.userAddress = userAddress;
    this.createdAt = createdAt;
    this.userSignType = userSignType;
  }

  public int getUserSeq() {
    return userSeq;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public String getUserName() {
    return userName;
  }

  public String getUserBirth() {
    return userBirth;
  }

  public String getUserPhoneNumber() {
    return userPhoneNumber;
  }

  public String getUserAddress() {
    return userAddress;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getUserSignType() {
    return userSignType;
  }

  
  
  
}
