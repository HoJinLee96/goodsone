package dto;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class EstimateDto {
  private int estimateSeq;
  private String name;
  private String phone;
  private String email;
  private boolean emailAgree;
  private boolean smsAgree;
  private boolean callAgree;
  private String postcode;
  private String mainAddress;
  private String detailAddress;
  private String content;
  private List<MultipartFile> images;
  private String imagesPath;
  private Status status; 
  private LocalDateTime createdAt;
  public EstimateDto() {
  }
  // enum 선언
  public enum Status {
    접수, 답변중, 완료
  }


  public EstimateDto(String name, String phone, String email, boolean emailAgree, boolean smsAgree,
      boolean callAgree, String postcode, String mainAddress, String detailAddress, String content,
      List<MultipartFile> images, String imagesPath, Status status, LocalDateTime createdAt) {
    super();
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.emailAgree = emailAgree;
    this.smsAgree = smsAgree;
    this.callAgree = callAgree;
    this.postcode = postcode;
    this.mainAddress = mainAddress;
    this.detailAddress = detailAddress;
    this.content = content;
    this.images = images;
    this.imagesPath = imagesPath;
    this.status = status;
    this.createdAt = createdAt;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
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
  
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEmailAgree() {
    return emailAgree;
  }

  public void setEmailAgree(boolean emailAgree) {
    this.emailAgree = emailAgree;
  }
  

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  

  public String getImagesPath() {
    return imagesPath;
  }

  public void setImagesPath(String imagesPath) {
    this.imagesPath = imagesPath;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  
  
  
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  @Override
  public String toString() {
    return "EstimateDto [name=" + name + ", phone=" + phone + ", email=" + email + ", emailAgree="
        + emailAgree + ", smsAgree=" + smsAgree + ", callAgree=" + callAgree + ", postcode="
        + postcode + ", mainAddress=" + mainAddress + ", detailAddress=" + detailAddress
        + ", content=" + content + ", imagesPath=" + imagesPath + ", status="
        + status + ", createdAt=" + createdAt + "]";
  }

  
}