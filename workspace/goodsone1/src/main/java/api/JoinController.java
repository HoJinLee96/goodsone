package api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dto.UserRegister;
import service.UserServices;

@RestController
@RequestMapping("/api")
public class JoinController {
  
  
  private UserServices userService;

  @Autowired
  public JoinController(UserServices userService) {
    this.userService = userService;
  }
  
  @PostMapping("/joinNormal")
  public ResponseEntity<String> registerUser(@RequestParam UserRegister userRegister){
    
    return null;
  }

}
