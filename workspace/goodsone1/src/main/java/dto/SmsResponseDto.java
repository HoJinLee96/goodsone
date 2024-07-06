package dto;

import java.time.LocalDateTime;

public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    
    private String smsConfirmNum;
    private String to;

    // 기본 생성자
    public SmsResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public SmsResponseDto(String requestId, LocalDateTime requestTime, String statusCode, String statusName) {
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

    public String getSmsConfirmNum() {
        return smsConfirmNum;
    }

    public String getTo() {
      return to;
  }

    public void setSmsConfirmNum(String smsConfirmNum) {
      this.smsConfirmNum = smsConfirmNum;
    }

    public void setTo(String to) {
      this.to = to;
    }
    
    
   
}