package api;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AddressDto;
import dto.UserDto;
import service.UserServices;

@RestController
@RequestMapping("/api")
public class JoinController {
  
  
  private UserServices userService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public JoinController(UserServices userService) {
    this.userService = userService;
  }
  
  @PostMapping("/validate/email")
  public ResponseEntity<String> isEmailExists(@RequestParam String reqEmail) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      if(userService.isEmailExists(reqEmail)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("가입 불가능한 이메일 입니다.");}
      else {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("가입 가능한 이메일 입니다.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
  }
  
  @PostMapping("/validate/phone")
  public ResponseEntity<String> isPhoneExists(@RequestParam String reqPhone) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      if(userService.isPhoneExists(reqPhone)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body("가입 불가능한 번호 입니다.");}
      else {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("가입 가능한 번호 입니다.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
  }
  
  
  @PostMapping("/join/public")
  public ResponseEntity<String> register(@RequestBody Map<String, Object> request) {
    UserDto userDto = objectMapper.convertValue(request.get("user"), UserDto.class);
    AddressDto addressDto = objectMapper.convertValue(request.get("address"), AddressDto.class);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      userService.registerUser(userDto, addressDto);
      return ResponseEntity.status(HttpStatus.OK).headers(headers).body("회원가입 성공");
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
  }

}
