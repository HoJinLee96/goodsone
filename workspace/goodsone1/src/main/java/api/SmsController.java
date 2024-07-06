package api;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.management.openmbean.InvalidKeyException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import service.RateLimiterService;
import service.SmsService;

@RestController
@RequestMapping("/api")

public class SmsController {
  private final SmsService smsService;
  private final RateLimiterService rateLimiterService;

  @Autowired
  public SmsController(SmsService smsService, RateLimiterService rateLimiterService) {
    this.rateLimiterService = rateLimiterService;
    this.smsService = smsService;
  }

  @PostMapping("/verify/sendsms")
  public ResponseEntity<?> sendSms(@RequestParam String phoneNumber, HttpServletRequest request)
      throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException,
      JsonProcessingException, RestClientException, InvalidKeyException,
      java.security.InvalidKeyException {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");

    String clientIp = request.getRemoteAddr();
    if (!rateLimiterService.isAllowed(clientIp)) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(headers).body("요청 횟수 초과. 잠시 후 시도 해주세요.");
    }

    int i = 0;
    try {
      i = smsService.sendSms(phoneNumber);
    } catch (JsonProcessingException | RestClientException | InvalidKeyException
        | java.security.InvalidKeyException | NoSuchAlgorithmException
        | UnsupportedEncodingException | URISyntaxException | SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 이용할 수 없습니다.");
    }
    return ResponseEntity.ok(i);
  }
  
  @PostMapping("/verify/confirmsms")
  public ResponseEntity<?> verifySms(@RequestParam String seq, @RequestParam String reqCode){
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    
    try {
      if(reqCode.equals(smsService.confirmSms(seq)))
        return ResponseEntity.ok("성공");
      else
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("인증번호를 다시 확인해주세요.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 이용할 수 없습니다.");
    }
    
  }
}
