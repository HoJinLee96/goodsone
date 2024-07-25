package api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dto.VerifyResponseDto;
import service.VerificationServices;

@RestController
@RequestMapping("/api")
public class VerificationController {

  VerificationServices verificationServices;

  @Autowired
  public VerificationController(VerificationServices verificationServices) {
    super();
    this.verificationServices = verificationServices;
  }

  @PostMapping("/verify/comparecode")
  public ResponseEntity<?> verifySms(HttpServletRequest req,@RequestParam String reqCode) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    HttpSession session = req.getSession(false);
    VerifyResponseDto verifyResponseDto = (VerifyResponseDto) session.getAttribute("verifyResponseDto");
    if(verifyResponseDto ==null)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("인증번호 기간이 완료 되었습니다. 재발급 시도해 주세요.");
    LocalDateTime reqTime = verifyResponseDto.getRequestTime();
    long reqTimeMillis = reqTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    long nowTimeMillis = System.currentTimeMillis();
    System.out.println(verifyResponseDto.toString());

    if (Math.abs(nowTimeMillis - reqTimeMillis) > 180000)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("인증번호 기간이 완료 되었습니다. 재발급 시도해 주세요.");
    try {
      if (verificationServices.compareCode(verifyResponseDto,reqCode))
        return ResponseEntity.ok("성공");
      else
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("인증번호를 다시 확인해주세요.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("현재 이용할 수 없습니다.");
    }
  }
}
