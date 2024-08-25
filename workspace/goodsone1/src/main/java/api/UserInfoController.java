package api;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dto.UserDto;
import exception.NotFoundException;
import service.UserServices;

@RestController
@RequestMapping("/user")
public class UserInfoController {

  private UserServices userService;

  @Autowired
  public UserInfoController(UserServices userService) {
    this.userService = userService;
  }
  
  @PostMapping("/get/emailByPhone")
  public ResponseEntity<String> getEmailByPhone(@RequestParam("phone") String reqPhone) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    
    try {
      String email = userService.getEmailByPhone(reqPhone);
      return ResponseEntity.status(HttpStatus.OK).headers(headers).body(email);

    } catch (NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
    }
  }    
  
  @PostMapping("/exist/emailPhone")
  public ResponseEntity<?> isEmailPhoneExist(@RequestParam("email") String reqEmail,@RequestParam("phone") String reqPhone) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    String email = getEmailByPhone(reqPhone).getBody();
    if(reqEmail.equals(email)) {
      return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).build();
  }
  
  @PostMapping("/update/password")
  public ResponseEntity<String> updatePassword(
      @RequestParam("email") String reqEmail,
      @RequestParam("phone") String reqPhone,
      @RequestParam("password") String reqPassword,
      @RequestParam("confirmPassword") String reqConfirmPassword) {
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    
    //두 비밀번호 값 불일치
    if(!reqPassword.equals(reqConfirmPassword)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
    }
    
      try {
        UserDto userDto = userService.getUserByEmail(reqEmail);
        
        
        userService.updateUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
      } catch (NotFoundException e) {
        e.printStackTrace();
        // 이메일에 일치하는 계정 존재 하지 않음.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
      } catch (SQLException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
      }

  } 
  
}
