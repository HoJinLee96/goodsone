package dto;

import java.time.LocalDateTime;

public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
    private String smsConfirmNum;

    // 기본 생성자
    public SmsResponseDto() {}

    // 모든 필드를 포함하는 생성자
    public SmsResponseDto(String requestId, LocalDateTime requestTime, String statusCode, String statusName, String smsConfirmNum) {
        this.requestId = requestId;
        this.requestTime = requestTime;
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.smsConfirmNum = smsConfirmNum;
    }

    // 특정 필드만 포함하는 생성자
    public SmsResponseDto(String smsConfirmNum) {
        this.smsConfirmNum = smsConfirmNum;
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

    // 빌더 패턴 구현
    public static class SmsResponseDtoBuilder {
        private String requestId;
        private LocalDateTime requestTime;
        private String statusCode;
        private String statusName;
        private String smsConfirmNum;

        public SmsResponseDtoBuilder() {}

        public SmsResponseDtoBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public SmsResponseDtoBuilder requestTime(LocalDateTime requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public SmsResponseDtoBuilder statusCode(String statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public SmsResponseDtoBuilder statusName(String statusName) {
            this.statusName = statusName;
            return this;
        }

        public SmsResponseDtoBuilder smsConfirmNum(String smsConfirmNum) {
            this.smsConfirmNum = smsConfirmNum;
            return this;
        }

        public SmsResponseDto build() {
            return new SmsResponseDto(requestId, requestTime, statusCode, statusName, smsConfirmNum);
        }
    }

    public static SmsResponseDtoBuilder builder() {
        return new SmsResponseDtoBuilder();
    }
}