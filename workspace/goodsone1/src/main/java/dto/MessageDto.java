package dto;

public class MessageDto {
    private String to;

    // 기본 생성자
    public MessageDto() {}

    // 모든 필드를 포함하는 생성자
    public MessageDto(String to) {
        this.to = to;
    }

    // Getter 메서드
    public String getTo() {
        return to;
    }

    // Builder 패턴 구현
    public static class MessageDtoBuilder {
        private String to;

        public MessageDtoBuilder() {}

        public MessageDtoBuilder to(String to) {
            this.to = to;
            return this;
        }

        public MessageDto build() {
            return new MessageDto(to);
        }
    }

    public static MessageDtoBuilder builder() {
        return new MessageDtoBuilder();
    }
}