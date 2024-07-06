package main;

import java.util.Arrays;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.MessageDto;
import dto.SmsRequestDto;

public class TestMain {
    public static void main(String[] args) {
        MessageDto message1 = new MessageDto("1234567890");
        SmsRequestDto smsRequest = SmsRequestDto.builder()
                .type("SMS")
                .contentType("text")
                .countryCode("1")
                .from("9876543210")
                .content("Test message")
                .messages(Arrays.asList(message1))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(smsRequest);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}