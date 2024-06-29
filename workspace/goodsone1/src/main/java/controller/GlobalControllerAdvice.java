package controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@ModelAttribute("user")
	public String addUserToModel(HttpSession session) {
		return (String) session.getAttribute("user");
	}
}
