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
  
}
