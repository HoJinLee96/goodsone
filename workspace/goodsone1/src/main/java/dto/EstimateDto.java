package dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class EstimateDto {
  private String phone;
  private boolean pageAgree;
  private boolean smsAgree;
  private boolean callAgree;
  private String postcode;
  private String mainAddress;
  private String detailAddress;
  private String content;
  private List<MultipartFile> images;
  public EstimateDto() {
  }
  public EstimateDto(String phone, boolean pageAgree, boolean smsAgree, boolean callAgree,
      String postcode, String mainAddress, String detailAddress, String content,
      List<MultipartFile> images) {
    super();
    this.phone = phone;
    this.pageAgree = pageAgree;
    this.smsAgree = smsAgree;
    this.callAgree = callAgree;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
    this.content = content;
    this.images = images;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public boolean isPageAgree() {
    return pageAgree;
  }
  public void setPageAgree(boolean pageAgree) {
    this.pageAgree = pageAgree;
  }
  public boolean isSmsAgree() {
    return smsAgree;
  }
  public void setSmsAgree(boolean smsAgree) {
    this.smsAgree = smsAgree;
  }
  public boolean isCallAgree() {
    return callAgree;
  }
  public void setCallAgree(boolean callAgree) {
    this.callAgree = callAgree;
  }
  public String getPostcode() {
    return postcode;
  }
  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }
  public String getMainAddress() {
    return mainAddress;
  }
  public void setMainAddress(String mainAddress) {
    this.mainAddress = mainAddress;
  }
  public String getDetailAddress() {
    return detailAddress;
  }
  public void setDetailAddress(String detailAddress) {
    this.detailAddress = detailAddress;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }
  public List<MultipartFile> getImages() {
    return images;
  }
  public void setImages(List<MultipartFile> images) {
    this.images = images;
  }
  @Override
  public String toString() {
    return "EstimateDTO [phone=" + phone + ", pageAgree=" + pageAgree + ", smsAgree=" + smsAgree
        + ", callAgree=" + callAgree + ", postcode=" + postcode + ", mainAddress=" + mainAddress
        + ", detailAddress=" + detailAddress + ", content=" + content + ", images=" + images + "]";
  }
  
  
}