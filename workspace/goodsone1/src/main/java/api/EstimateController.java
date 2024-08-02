package api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import dto.EstimateDto;
import dto.EstimateDto.Status;
import service.EstimateService;

@RestController
public class EstimateController {
  
  EstimateService estimateService;

  @Autowired
  public EstimateController(EstimateService estimateService) {
    this.estimateService = estimateService;
  }

  @PostMapping("/estimate")
  public ResponseEntity<?> registerEstimate(
      @RequestParam("name") String name,
      @RequestParam("phone") String phone,
      @RequestParam("email") String email,
      @RequestParam(value = "emailAgree", required = false, defaultValue = "false") Boolean emailAgree,
      @RequestParam(value = "smsAgree", required = false, defaultValue = "false") Boolean smsAgree,
      @RequestParam(value = "callAgree", required = false, defaultValue = "false") Boolean callAgree,
      @RequestParam("postcode") String postcode,
      @RequestParam("mainAddress") String mainAddress,
      @RequestParam("detailAddress") String detailAddress,
      @RequestParam("content") String content,
      @RequestParam(value="images",required = false) List<MultipartFile> images) {
    System.out.println("EstimateController.registerEstimate() 실행");

    EstimateDto estimateDTO = new EstimateDto();
    estimateDTO.setName(name);
    estimateDTO.setPhone(phone);
    estimateDTO.setEmail(email);
    estimateDTO.setEmailAgree(emailAgree);
    estimateDTO.setSmsAgree(smsAgree);
    estimateDTO.setCallAgree(callAgree);
    estimateDTO.setPostcode(postcode);
    estimateDTO.setMainAddress(mainAddress);
    estimateDTO.setDetailAddress(detailAddress);
    estimateDTO.setContent(content);
    estimateDTO.setImages(images);
    estimateDTO.setStatus(Status.접수);
      
    System.out.println(estimateDTO.toString()); 
    
    try {
      estimateService.registerEstimate(estimateDTO);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
      return ResponseEntity.ok("성공");
  }

}
