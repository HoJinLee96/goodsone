package api;

import java.sql.SQLException;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AddressDto;
import dto.RegisterUserDto;
import dto.UserDto;
import exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        if (referer == null || !referer.startsWith(req.getScheme() + "://" + req.getServerName()) || referer.contains("/login") ||referer.contains("/join")) {
          referer="/home";
        } 

        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("redirectUrl", referer);

        return ResponseEntity.status(HttpStatus.OK).body(response);
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
  
  @PostMapping("/update/password")
  public ResponseEntity<?> updatePassword(
      @RequestParam("email") String reqEmail,
      @RequestParam("phone") String reqPhone,
      @RequestParam("password") String reqPassword,
      @RequestParam("confirmPassword") String reqConfirmPassword) {
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    
    //두 비밀번호 값 불일치
    if(!reqPassword.equals(reqConfirmPassword))
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
    
      try {
        UserDto userDto = userService.getUserByEmail(reqEmail);
        System.out.println(userDto.getPhone() +"\n" + reqPhone + "\n" + userDto.getPhone().equals(reqPhone));
        if(!userDto.getPhone().equals(reqPhone))
          throw new NotFoundException();
        
        userService.updatePassword(userDto.getUserSeq(),reqPassword);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        
      } catch (NotFoundException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
      } catch (SQLException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
      }

  } 
  
  @PostMapping("/exist/email")
  public ResponseEntity<String> isEmailExists(@RequestParam String reqEmail) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      if(userService.isEmailExists(reqEmail)) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).headers(headers).body("가입 불가능한 이메일 입니다.");}
      else {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body("가입 가능한 이메일 입니다.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
  }
  
  @PostMapping("/exist/phone")
  public ResponseEntity<String> isPhoneExists(@RequestParam String reqPhone) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      if(userService.isPhoneExists(reqPhone)) {
        return ResponseEntity.status(HttpStatus.MULTI_STATUS).headers(headers).build();}
      else {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("서버 장애 발생.");
    }
  }
  
  @PostMapping("/exist/emailPhone")
  public ResponseEntity<?> isEmailPhoneExist(@RequestParam("email") String reqEmail,@RequestParam("phone") String reqPhone) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
    try {
      if(userService.isEmailPhoneExists(reqEmail, reqPhone)) {
        return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
    }
  }
  
  @PostMapping("/join/once")
  public ResponseEntity<String> public1(@RequestBody Map<String, Object> request, HttpServletRequest httpServletRequest) {
    ObjectMapper objectMapper = new ObjectMapper();
    RegisterUserDto registerUserDto = objectMapper.convertValue(request.get("registerUserDto"), RegisterUserDto.class);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    HttpSession session = httpServletRequest.getSession();
    session.setAttribute("registerUserDto", registerUserDto);
    
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body("계성 생성 완료.");

  }

  @PostMapping("/join/seconde")
  public ResponseEntity<String> public2(@RequestBody Map<String, Object> reqData, HttpServletRequest req) {
    ObjectMapper objectMapper = new ObjectMapper();
    UserDto userDto = objectMapper.convertValue(reqData.get("userDto"), UserDto.class);
    AddressDto addressDto = objectMapper.convertValue(reqData.get("addressDto"), AddressDto.class);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "text/plain; charset=UTF-8");
      
    try {
      userService.registerUser(userDto, addressDto);
      return ResponseEntity.status(HttpStatus.OK).headers(headers).body("회원가입 완료.");
    } catch (SQLException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("가입이 불가능 합니다.");
      }
    }
  
}
