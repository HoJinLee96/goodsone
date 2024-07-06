package dto;

public class MessageDto {
    private String to;

    
    public MessageDto() {
    }


    // 모든 필드를 포함하는 생성자
    public MessageDto(String to) {
        this.to = to;
    }


    public String getTo() {
      return to;
    }

}