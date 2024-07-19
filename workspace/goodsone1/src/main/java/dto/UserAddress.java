package dto;

public class UserAddress {
  private final String userPostcode;
  private final String userMainAddress;
  private final String userDetailAddress;
  
  public UserAddress(String userPostcode, String userMainAddress, String userDetailAddress) {
    super();
    this.userPostcode = userPostcode;
    this.userMainAddress = userMainAddress;
    this.userDetailAddress = userDetailAddress;
  }
  
  
}
