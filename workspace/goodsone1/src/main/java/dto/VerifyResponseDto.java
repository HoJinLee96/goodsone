package dto;

import java.time.LocalDateTime;

public class VerifyResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    
    private String verificationCode;
    private String to;

    // 기본 생성자
    public VerifyResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public VerifyResponseDto(String requestId, LocalDateTime requestTime, String statusCode, String statusName) {
        this.requestId = requestId;
        this.requestTime = requestTime;
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    // Getter 메서드들
    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getTo() {
      return to;
  }

    
    // Sertter 메서드들
    public void setVerificationCode(String verificationCode) {
      this.verificationCode = verificationCode;
    }

    public void setTo(String to) {
      this.to = to;
    }

    public void setRequestId(String requestId) {
      this.requestId = requestId;
    }

    public void setRequestTime(LocalDateTime requestTime) {
      this.requestTime = requestTime;
    }

    public void setStatusCode(String statusCode) {
      this.statusCode = statusCode;
    }

    public void setStatusName(String statusName) {
      this.statusName = statusName;
    }
    
}