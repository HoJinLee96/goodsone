package api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.management.openmbean.InvalidKeyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

import dto.MessageDto;
import dto.SmsResponseDto;
import service.SmsService;

@RestController
public class SmsController {
	private final SmsService smsService;
	
	@Autowired
    public SmsController(SmsService smsService) {
		this.smsService = smsService;
	}

	@PostMapping("/sms/send")
    public SmsResponseDto sendSms(@RequestBody MessageDto messageDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, JsonProcessingException, RestClientException, InvalidKeyException, java.security.InvalidKeyException {
		
		System.out.println(messageDto.getTo());
        SmsResponseDto responseDto = smsService.sendSms(messageDto);
        return responseDto;
    }
}