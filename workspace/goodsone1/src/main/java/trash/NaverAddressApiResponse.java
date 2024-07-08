package dto;

import java.util.List;

public class NaverAddressApiResponse {
  private String status;
  private String errorMessage;
  private Meta meta;
  private List<Address> addresses;
  
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getErrorMessage() {
    return errorMessage;
  }
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
  public Meta getMeta() {
    return meta;
  }
  public void setMeta(Meta meta) {
    this.meta = meta;
  }
  public List<Address> getAddresses() {
    return addresses;
  }
  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

 
  public static class Meta {
    private int totalCount;
    private int page;
    private int count;

    
      public int getTotalCount() {
      return totalCount;
    }
    public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
    }
    public int getPage() {
      return page;
    }
    public void setPage(int page) {
      this.page = page;
    }
    public int getCount() {
      return count;
    }
    public void setCount(int count) {
      this.count = count;
    }
   
  }

  public static class Address {
    private String roadAddress;
    private String jibunAddress;
    private String englishAddress;
    private String x;
    private String y;
    private double distance;
    private List<AddressElement> addressElements;
    
      public String getRoadAddress() {
      return roadAddress;
    }
    public void setRoadAddress(String roadAddress) {
      this.roadAddress = roadAddress;
    }
    public String getJibunAddress() {
      return jibunAddress;
    }
    public void setJibunAddress(String jibunAddress) {
      this.jibunAddress = jibunAddress;
    }
    public String getEnglishAddress() {
      return englishAddress;
    }
    public void setEnglishAddress(String englishAddress) {
      this.englishAddress = englishAddress;
    }
    public String getX() {
      return x;
    }
    public void setX(String x) {
      this.x = x;
    }
    public String getY() {
      return y;
    }
    public void setY(String y) {
      this.y = y;
    }
    public double getDistance() {
      return distance;
    }
    public void setDistance(double distance) {
      this.distance = distance;
    }
    public List<AddressElement> getAddressElements() {
      return addressElements;
    }
    public void setAddressElements(List<AddressElement> addressElements) {
      this.addressElements = addressElements;
    }


  }

  public static class AddressElement {
      // Define properties of address elements

      // Getters and setters
  }
}
