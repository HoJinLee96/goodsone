package dto;

public class AddressDto {
  private int addressSeq;
  private int userSeq;
  private int postcode;
  private String mainAddress;
  private String detailAddress;
  
  public AddressDto() {}

  public AddressDto(int userSeq ,int postcode, String mainAddress, String detailAddress) {
    this.userSeq = userSeq;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
  }
  
  public AddressDto(int addressSeq, int userSeq, int postcode, String mainAddress,
      String detailAddress) {
    super();
    this.addressSeq = addressSeq;
    this.userSeq = userSeq;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
  }

  public int getAddressSeq() {
    return addressSeq;
  }

  public int getUserSeq() {
    return userSeq;
  }

  public int getPostcode() {
    return postcode;
  }

  public String getMainAddress() {
    return mainAddress;
  }

  public String getDetailAddress() {
    return detailAddress;
  }
  
  
}
