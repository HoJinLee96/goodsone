package dto;

import java.time.LocalDateTime;

public class AddressDto {
  private int addressSeq;
  private int userSeq;
  private String name;
  private int postcode;
  private String mainAddress;
  private String detailAddress;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  
  public AddressDto() {}
  
  public AddressDto(int postcode, String mainAddress, String detailAddress) {
    super();
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
  }



  public AddressDto(int userSeq, String name, int postcode, String mainAddress, String detailAddress) {
    this.userSeq = userSeq;
    this.name = name;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
  }
  
  public AddressDto(int addressSeq, int userSeq, String name, int postcode, String mainAddress,
      String detailAddress, LocalDateTime createdAt, LocalDateTime updatedAt) {
    super();
    this.addressSeq = addressSeq;
    this.userSeq = userSeq;
    this.name = name;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "AddressDto [addressSeq=" + addressSeq + ", userSeq=" + userSeq + ", postcode="
        + postcode + ", mainAddress=" + mainAddress + ", detailAddress=" + detailAddress
        + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
  }
  
  
  
  
}
