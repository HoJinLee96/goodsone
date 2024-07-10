package service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.VerificationRepositoryDao;
import dto.MessageDto;
import dto.SmsRequestDto;
import dto.SmsResponseDto;

@PropertySource("classpath:application.properties")
@Service
public class SmsService {
	
	private final VerificationRepositoryDao verificationRepositoryDao;
	
	@Value("${naver-api.accessKey}")
	private String accessKey;

	@Value("${naver-sms.serviceId}")
	private String serviceId;

	@Value("${naver-sms.senderPhone}")
	private String phone;

	@Autowired
	public SmsService(VerificationRepositoryDao verificationRepositoryDao) {
		this.verificationRepositoryDao = verificationRepositoryDao;
	}
	
	@PostConstruct
    private void init() {
        System.out.println("serviceId: " + serviceId);
        System.out.println("phone: " + phone);
    }

	public int sendSms(String phoneNumber) throws JsonProcessingException, RestClientException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, java.security.InvalidKeyException, SQLException {
	 init();
	  System.out.println("인증번호 발생 시도 : "+phoneNumber);
	    //인증번호 생성
	    String smsConfirmNum = createSmsKey();
	    System.out.println("인증번호 발생 : "+ smsConfirmNum);
	    
		// 현재시간
		String time = Long.toString(System.currentTimeMillis());
		
		// url
	    String url = "/sms/v2/services/" + this.serviceId + "/messages";
		
		// 서명 생성
	    NaverCreateSignature creater = new NaverCreateSignature();
	    
		// 헤더세팅
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time);
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", creater.getSignature("POST", url, time)); // signature 서명
		headers.add("Content-Type", "application/json; charset=UTF-8");

		MessageDto messageDto = new MessageDto(phoneNumber);
		List<MessageDto> messages = new ArrayList<>();
		messages.add(messageDto);

		// api 요청 양식에 맞춰 세팅
		SmsRequestDto request = SmsRequestDto.builder().type("SMS").contentType("COMM").countryCode("82").from(phone)
				.content("[ goodsone1 인증번호 ]"+ "\n" +  "[" + smsConfirmNum + "]를 입력해주세요").messages(messages).build();

		// request를 json형태로 body로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
		String body = objectMapper.writeValueAsString(request);
		
		// body와 header을 합친다
		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

		// restTemplate를 통해 외부 api와 통신
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters()
	    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

		SmsResponseDto responseDto = restTemplate.postForObject(
				new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"), httpBody,
				SmsResponseDto.class);
		
//		SmsResponseDto responseDto = new SmsResponseDto(
//				serviceId,
//				new Timestamp(Long.parseLong(time)).toLocalDateTime(),
//                "200",
//                "success");
		
		responseDto.setSmsConfirmNum(smsConfirmNum);
		responseDto.setTo(phoneNumber);
		
		return verificationRepositoryDao.sendSmsSave(responseDto);
	}

// 전달하고자 하는 데이터를 암호화해주는 작업
//	private String getSignature(String time)
//			throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, java.security.InvalidKeyException {
//		String space = " ";
//		String newLine = "\n";
//		String method = "POST";
//		String url = "/sms/v2/services/" + this.serviceId + "/messages";
//		String accessKey = this.accessKey;
//		String secretKey = this.secretKey;
//
//		String message = new StringBuilder().append(method).append(space).append(url).append(newLine).append(time)
//				.append(newLine).append(accessKey).toString();
//
//		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
//		Mac mac = Mac.getInstance("HmacSHA256");
//		mac.init(signingKey);
//
//		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
//		
//	    System.out.println("시그니처 생성완료");
//		return encodeBase64String;
//	}

//5자리의 난수를 조합을 통해 인증코드 만들기
	private String createSmsKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 5; i++) { // 인증코드 5자리
			key.append((rnd.nextInt(10)));
		}
		return key.toString();
	}
	
	public String confirmSms(String verSeq) throws SQLException, Exception {
	  return verificationRepositoryDao.getConfirmNumber(Integer.valueOf(verSeq)).orElseThrow(()->new Exception("잠시후 시도 해주세요."));
	}
}