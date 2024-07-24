package dto;

public class UserAddressDto {
  private final String userPostcode;
  private final String userMainAddress;
  private final String userDetailAddress;
  
  public UserAddressDto(String userPostcode, String userMainAddress, String userDetailAddress) {
    super();
    this.userPostcode = userPostcode;
    this.userMainAddress = userMainAddress;
    this.userDetailAddress = userDetailAddress;
  }
  
  
}
