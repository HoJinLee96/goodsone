package api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.EstimateDto;
import dto.EstimateDto.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EstimateService;

@RestController
@RequestMapping("/estimate")
public class EstimateController {
  
  EstimateService estimateService;

  @Autowired
  public EstimateController(EstimateService estimateService) {
    this.estimateService = estimateService;
  }

  @PostMapping("/register")
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
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      

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
    estimateDTO.setStatus(Status.RECEIVED);
      
    System.out.println(estimateDTO.toString()); 
    
    try {
      estimateService.registerEstimate(estimateDTO);
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
    
      return ResponseEntity.ok("성공");
  }
  
  @PostMapping("/speedRegister")
  public ResponseEntity<?> speedRegisterEstimate(
      @RequestParam("phone") String phone,
      @RequestParam("cleaningService") String cleaningService,
      @RequestParam("region") String region) {
    System.out.println("EstimateController.speedRegisterEstimate() 실행");
    
    EstimateDto estimateDTO = new EstimateDto(phone, cleaningService, region, Status.RECEIVED);
    
    try {
      estimateService.registerEstimate(estimateDTO);
      return ResponseEntity.status(HttpStatus.OK).build();
      
    } catch (SQLException | IOException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } 
    
  }

  @GetMapping("/getAllEstimate")
  public ResponseEntity<?> getAllEstimate(HttpServletRequest req, HttpServletResponse res) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=UTF-8");

      System.out.println("EstimateController.getAllEstimate() 실행");
      int page = Integer.parseInt(req.getParameter("page"));
      System.out.println("page = " + page);
      List<EstimateDto> list = null;
      try {
          list = estimateService.getAllEstimate(page);
      } catch (SQLException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .headers(headers)
                               .body("{\"error\": \"An error occurred while fetching the estimates.\"}");
      }

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      String jsonResponse = "";
      try {
          jsonResponse = objectMapper.writeValueAsString(list);
      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .headers(headers)
                               .body("{\"error\": \"An error occurred while processing the estimates.\"}");
      }

      return ResponseEntity.ok()
                           .headers(headers)
                           .body("{\"list\": " + jsonResponse + "}");
  }
      
  @GetMapping("/getCountAll")
  public ResponseEntity<?> getCountAll(HttpServletRequest req, HttpServletResponse res){
      HttpHeaders headers = new HttpHeaders();
      headers.add("Content-Type", "application/json; charset=UTF-8");

      int total = 0;
      try {
          total = estimateService.getCountAll();
      } catch (SQLException e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .headers(headers)
                               .body("{\"error\": \"An error occurred while fetching the count.\"}");
      }
      return ResponseEntity.ok()
                           .headers(headers)
                           .body("{\"totalCount\": " + total + "}");
  }
    
  
}
