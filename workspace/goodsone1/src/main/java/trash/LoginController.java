package trash;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import dto.UserDto;
import exception.NotFoundException;
import service.UserService;

@Controller
public class LoginController {

  private UserService userServices;

  @Autowired
  public LoginController(UserService userServices) {
    this.userServices = userServices;
  }

//  @PostMapping("/loginByEmail")
//  public void loginByEmail(
//      @RequestParam("email") String reqEmail,
//      @RequestParam("password") String reqPassword,
//      @RequestParam(value = "rememmberIdCheckbox", required = false, defaultValue = "false") boolean rememberId,
//      HttpSession session, HttpServletRequest req, HttpServletResponse res,
//      RedirectAttributes redirectAttributes) {
//    System.out.println("LoginController.loginByEmail() 시작");
//    res.setContentType("text/html; charset=UTF-8");
//    res.setCharacterEncoding("UTF-8");
//    PrintWriter out = null;
//    try {
//      out = res.getWriter();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//
//    try {
//      String password = userServices.getPasswordByEmail(reqEmail);
//      if (passwordEncoder.matches(reqPassword, password)) {
//        System.out.println("로그인 성공");
//        UserDto userDto = userServices.getUserByEmail(reqEmail);
//        session.setAttribute("userDto", userDto);
//        session.setMaxInactiveInterval(30 * 60); // 세션 만료 시간: 30분
//        
//        // 이전 페이지의 도메인 확인
//        String referer = (String) session.getAttribute("previousPageUrl");
//        if (!(referer != null && referer.startsWith(req.getScheme() + "://" + req.getServerName()) && !referer.contains("/login"))) {
//          referer="/home";
//        } 
//        
//        res.setContentType("text/html; charset=UTF-8");
//        // 아이디 저장 체크 확인
//        if(rememberId) {
//          out.println("<script>localStorage.setItem('chamRememmberUserId', '" + reqEmail + "'); location.href='" + referer + "';</script>");
//        }else {
//          out.println("<script>localStorage.removeItem('chamRememmberUserId'); location.href='" + referer + "';</script>");
//        }
//
//      } else {
////        redirectAttributes.addFlashAttribute("message", "비밀번호가 일치하지 않습니다.");
//        out.println("<script>alert('비밀번호가 일치하지 않습니다.'); location.href='/login';</script>");
//        out.println("<script>location.href='/login';</script>");
//      }
//    } catch (NotFoundException e) {
////      redirectAttributes.addFlashAttribute("message", "일치하는 회원정보가 없습니다.");
////      out.println("<script>location.href='/login';</script>");
//      out.println("<script>alert('일치하는 회원정보가 없습니다.'); location.href='/login';</script>");
//    } catch (SQLException e) {
//      e.printStackTrace();
////      redirectAttributes.addFlashAttribute("message", "현재 접속할 수 없습니다.");
//      out.println("<script>alert('현재 접속할 수 없습니다.'); location.href='/login';</script>");
//    }
//  }

}
