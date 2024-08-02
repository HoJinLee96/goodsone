package api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import dto.EstimateDto;

@RestController
public class EstimateController {
  
  @PostMapping("/estimate")
  public ResponseEntity<?> registerEstimate(
      @RequestParam("phone") String name,
      @RequestParam("phone") String phone,
      @RequestParam("email") String email,
      @RequestParam(value = "emailAgree", required = false, defaultValue = "false") Boolean emailAgree,
      @RequestParam(value = "smsAgree", required = false, defaultValue = "false") Boolean smsAgree,
      @RequestParam(value = "callAgree", required = false, defaultValue = "false") Boolean callAgree,
      @RequestParam("postcode") String postcode,
      @RequestParam("mainAddress") String mainAddress,
      @RequestParam("detailAddress") String detailAddress,
      @RequestParam("content") String content,
      @RequestParam("images") List<MultipartFile> images) {
    System.out.println("WebMainController.registerEstimate() 실행");

    EstimateDto estimateDTO = new EstimateDto();
    estimateDTO.setPhone(phone);
    estimateDTO.setPhone(email);
    estimateDTO.setEmailAgree(emailAgree);
    estimateDTO.setSmsAgree(smsAgree);
    estimateDTO.setCallAgree(callAgree);
    estimateDTO.setPostcode(postcode);
    estimateDTO.setMainAddress(mainAddress);
    estimateDTO.setDetailAddress(detailAddress);
    estimateDTO.setContent(content);
    estimateDTO.setImages(images);
      

      return ResponseEntity.ok("성공");
  }

}
