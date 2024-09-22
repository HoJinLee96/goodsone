package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class WebMainController {
  
  @GetMapping({"/", "/home"})
  public String showHome(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("----------WebMainController.showHome() 실행----------");
    return "main_home";
  }


  @GetMapping("/login")
  public String showLogin(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
    System.out.println("----------WebMainController.showLogin() 실행----------");
    // 이전 페이지의 도메인 확인
   String referer = req.getHeader("Referer");
   if (referer != null && referer.startsWith(req.getScheme() + "://" + req.getServerName()) && !referer.contains("/login") && !referer.contains("/join")) {
     session.setAttribute("previousPageUrl", referer);
   }
    return "login";
  }
  
  @GetMapping("/loginBlank")
  public String showLoginBlank(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
    System.out.println("----------WebMainController.showLoginBlank() 실행----------");
    return "loginBlank";
  }
  
  @GetMapping("/clearLogin")
  public String showClearLogin(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
    System.out.println("----------WebMainController.showClearLogin() 실행----------");
    
    // 기존 세션이 존재하면 무효화
    if (session != null) {
        session.invalidate();  // 기존 세션 무효화
        System.out.println("기존 세션 무효화");
    }

    return "login";
  }
  
  @GetMapping("/logout")
  public String logout(HttpServletRequest req, HttpServletResponse res,HttpSession session) {
    System.out.println("----------WebMainController.logout() 실행----------");
    // 세션에서 사용자 정보를 제거하여 로그아웃 처리

    if (session != null) {
      session.removeAttribute("userDto");
      session.removeAttribute("oAuthDto");
      session.removeAttribute("oAuthToken");
      session.removeAttribute("oAuthTokenExpiry");
    }
    return "redirect:/home";
  }

  @GetMapping("/my")
  public String showMy(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("----------WebMainController.showMy() 실행----------");
    return "my";
  }

  @GetMapping("/join")
  public String showJoin(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("----------WebMainController.showJoin() 실행----------");
    return "join";
  }

   @GetMapping("/joinDetail")
   public String showJoin2(HttpServletRequest req, HttpServletResponse res) {
   System.out.println("----------WebMainController.joinDetail() 실행----------");
//   HttpSession session = req.getSession();
//   RegisterUserDto registerUserDto = (RegisterUserDto) session.getAttribute("registerUserDto");
//   if (registerUserDto == null) {
//   return "redirect:/join";
//   }
   return "joinDetail";
   }
   
   @GetMapping("/joinSuccess")
   public String showJoinSuccess(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showJoinSuccess() 실행----------");
     return "joinSuccess";
   }
   
   @GetMapping("/estimate")
   public String showEstimate(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showEstimate() 실행----------");
     return "estimate";
   }
   
   @GetMapping("/review")
   public String showReview(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showReview() 실행----------");
     return "review";
   }
   
   @GetMapping("/join/sns/confirm")
   public String showJoinSnsConfirm(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showJoinSnsConfirm() 실행----------");
     return "joinSnsConfirm";
   }
   
   @GetMapping("/find/email")
   public String showFindEmail(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showFindEmail() 실행----------");
     return "findEmailBlank";
   }
   
   @GetMapping("/find/password")
   public String showFindPassword(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showFindId() 실행----------");
     return "findPasswordBlank";
   }
   
   @GetMapping("/verifyUser")
   public String loginVerify(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.loginVerify() 실행----------");
     return "verifyUser";
   }

   @GetMapping("/verify/phone/blank")
   public String verifyPhoneBlank(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.verifyPhoneBlank() 실행----------");
     return "verifyPhoneBlank";
   }
   
   @GetMapping("/my/loginInfo")
   public String showMyLoginInfo(HttpServletRequest req, HttpServletResponse res) {
     System.out.println("----------WebMainController.showMyLoginInfo() 실행----------");
     return "myLoginInfo";
   }
}
