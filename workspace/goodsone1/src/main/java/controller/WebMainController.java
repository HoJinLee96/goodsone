package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import dto.RegisterUserDto;

@Controller
public class WebMainController {

  @GetMapping({"/", "/home"})
  public String showHome(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("WebMainController.showHome() 실행");
    return "main_home";
  }


  @GetMapping("/login")
  public String showLogin(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("WebMainController.showLogin() 실행");
    return "login";
  }


  @GetMapping("/logout")
  public String logout(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
    System.out.println("WebMainController.logout() 실행");
    // 세션에서 사용자 정보를 제거하여 로그아웃 처리

    if (session != null) {
      session.removeAttribute("user");
      session.removeAttribute("oAuthDto");
      session.removeAttribute("oAuthToken");
      session.removeAttribute("oAuthTokenExpiry");
    }
    return "redirect:/home";
  }

  @GetMapping("/my")
  public String showMy(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("WebMainController.showMy() 실행");
    return "my";
  }

  @GetMapping("/join")
  public String showJoin(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("WebMainController.showJoin() 실행");
    return "join";
  }

   @GetMapping("/joinDetail")
   public String showJoin2(HttpServletRequest req, HttpServletResponse res) {
   System.out.println("WebMainController.joinDetail() 실행");
   HttpSession session = req.getSession();
   RegisterUserDto registerUserDto = (RegisterUserDto) session.getAttribute("registerUserDto");
   if (registerUserDto == null) {
   return "redirect:/join";
   }
   return "joinDetail";
   }
   
   @GetMapping("/joinSuccess")
   public String showJoinSuccess(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("WebMainController.showJoinSuccess() 실행");
     return "joinSuccess";
   }

}
