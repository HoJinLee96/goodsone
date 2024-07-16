package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
  public ResponseEntity<?> verifySms(@RequestParam String seq, @RequestParam String reqCode) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");

    try {
      if (verificationServices.compareCode(seq,reqCode))
        return ResponseEntity.ok("성공");
      else
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers)
            .body("인증번호를 다시 확인해주세요.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers)
          .body("현재 이용할 수 없습니다.");
    }
  }
}
