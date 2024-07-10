package service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.MailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import dtoNaverMail.OutboundMailRequest;
import dtoNaverMail.RecipientForRequest;
import trash.NaverAddressApiResponse;


@Service
@PropertySource("classpath:application.properties")
public class NaverMailService {

  @Value("${naver-api.accessKey}")
  private String accessKey;

  private final String mailEndpoint = "https://mail.apigw.ntruss.com/api/v1";
  private final String sendMailUri = "/mails";

  // 메일 전송
  public void sendAuthMail(String mailAddress, String userName) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
    //인증번호 생성
    String smsConfirmNum = createSmsKey();
    System.out.println("인증번호 발생 : "+ smsConfirmNum);
    
    // 현재시간
    String time = Long.toString(System.currentTimeMillis());
    
    // 서명 생성
    NaverCreateSignature creater = new NaverCreateSignature();
    
    // 수신사 생성
    ArrayList<RecipientForRequest> recipients = new ArrayList<>();
    recipients.add(RecipientForRequest.of("mailAddress", "userName"));

    // 파라미터 세팅
    URI uri = UriComponentsBuilder.fromHttpUrl(mailEndpoint+sendMailUri)
        .queryParam("senderAddress", "") // String
        .queryParam("title", "[ goodsone1 인증번호 ]") // String
        .queryParam("body", "[ goodsone1 인증번호 ]"+ "\n" +  "[" + smsConfirmNum + "]를 입력해주세요") // String
        .queryParam("recipients", recipients) // List<RecipientForRequest>
        .queryParam("unsubscribeMessage", "수신 거부 ") // String
        .build()
        .encode()
        .toUri();
    
    // 헤더세팅
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-ncp-apigw-timestamp", time);
    headers.set("x-ncp-iam-access-key", accessKey);
    headers.set("x-ncp-apigw-signature-v2", creater.getSignature("POST", mailEndpoint+sendMailUri, time)); // signature 서명
    headers.set("x-ncp-lang ", "ko-KR"); //API 응답 값의 다국어 처리를 위한 값.
    headers.add("Content-Type", "application/json; charset=UTF-8");
    
    HttpEntity<String> entity = new HttpEntity<>(headers);


    
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

    
  }
  
//5자리의 난수를 조합을 통해 인증코드 만들기
  private String createSmsKey() {
      StringBuffer key = new StringBuffer();
      Random rnd = new Random();

      for (int i = 0; i < 5; i++) { // 인증코드 5자리
          key.append((rnd.nextInt(10)));
      }
      return key.toString();
  }

}
