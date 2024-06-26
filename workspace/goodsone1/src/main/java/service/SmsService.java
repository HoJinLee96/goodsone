package service;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.MessageDto;
import dto.Sms;
import dto.SmsRequestDto;
import dto.SmsResponseDto;

//application.yml의 값을 @Value로 전달받는다.
@PropertySource("classpath:application.yml")
@Service
public class SmsService {
	private final String smsConfirmNum = createSmsKey();
	private final SmsRepository smsRepository;

	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;

	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;

	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;

	@Value("${naver-cloud-sms.senderPhone}")
	private String phone;

	public SmsService(SmsRepository smsRepository) {
		this.smsRepository = smsRepository;
	}

	public SmsResponseDto sendSms(MessageDto messageDto) throws JsonProcessingException, RestClientException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, java.security.InvalidKeyException {

		// 현재시간
		String time = Long.toString(System.currentTimeMillis());

		// 헤더세팅
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature 서명
		
		System.out.println("시그니처 생성완료");

		List<MessageDto> messages = new ArrayList<>();
		messages.add(messageDto);

		// api 요청 양식에 맞춰 세팅
		SmsRequestDto request = SmsRequestDto.builder().type("SMS").contentType("COMM").countryCode("82").from(phone)
				.content("[ goodsone1 인증번호 ]"+ "\n" +  "[" + smsConfirmNum + "]를 입력해주세요").messages(messages).build();

		// request를 json형태로 body로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(request);
		
		// body와 header을 합친다
		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

		// restTemplate를 통해 외부 api와 통신
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

//		SmsResponseDto smsResponseDto = restTemplate.postForObject(
//				new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), httpBody,
//				SmsResponseDto.class);
		SmsResponseDto smsResponseDto = new SmsResponseDto(
				serviceId,
				new Timestamp(Long.parseLong(time)).toLocalDateTime(),
                "202",
                "성공");
		SmsResponseDto responseDto = new SmsResponseDto(smsConfirmNum);

		Sms sms = new Sms(messageDto.getTo(), responseDto.getSmsConfirmNum());
		smsRepository.save(sms);

		return smsResponseDto;
	}

// 전달하고자 하는 데이터를 암호화해주는 작업
	public String getSignature(String time)
			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, java.security.InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder().append(method).append(space).append(url).append(newLine).append(time)
				.append(newLine).append(accessKey).toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
		
		return encodeBase64String;
	}

//5자리의 난수를 조합을 통해 인증코드 만들기
	public static String createSmsKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 5; i++) { // 인증코드 5자리
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}
}