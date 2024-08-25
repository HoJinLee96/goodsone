package api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dto.UserDto;
import exception.NotFoundException;
import service.UserService;

@RestController
@RequestMapping("/user")
public class UserInfoController {

  private UserService userService;

  @Autowired
  public UserInfoController(UserService userService) {
    this.userService = userService;
  }
  
  @PostMapping("/login/email")
  public ResponseEntity<?> loginByEmail(
      @RequestParam("email") String reqEmail,
      @RequestParam("password") String reqPassword,
      @RequestParam(value = "rememmberIdCheckbox", required = false, defaultValue = "false") boolean rememberId,
      HttpSession session, HttpServletRequest req, HttpServletResponse res,
      RedirectAttributes redirectAttributes) {
    
    System.out.println("LoginController.loginByEmail() 시작");
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json; charset=UTF-8");
    
    try {
      if (userService.comparePasswordByEmail(reqEmail, reqPassword)) {
        UserDto userDto = userService.getUserByEmail(reqEmail);
        session.setAttribute("userDto", userDto);
        session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
        
        // 이전 페이지의 도메인 확인
        String referer = (String) session.getAttribute("previousPageUrl");
        if (!(referer != null && referer.startsWith(req.getScheme() + "://" + req.getServerName()) && !referer.contains("/login"))) {
          referer="/home";
        } 
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("redirectUrl", referer);

        return ResponseEntity.status(HttpStatus.OK).body(response);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(referer);
        }else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
        }
      }catch (SQLException | NotFoundException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();

    }
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
        userService.updatePassword(userDto.getUserSeq(),reqPassword);
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
