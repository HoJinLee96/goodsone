package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/master")
public class WebMasterController {
  
  @GetMapping("/home")
  public String showMasterHome(HttpServletRequest req, HttpServletResponse ress) {
    System.out.println("----------WebMainController.showMasterHome() 실행----------");
    return "masterHome";
  }
  
  @GetMapping("/estimateView")
  public String showEstimateView(HttpServletRequest req, HttpServletResponse res) {
    System.out.println("----------WebMainController.showEstimateView() 실행----------");
    return "estimateView";
  }

}
