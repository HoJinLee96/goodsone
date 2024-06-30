package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import service.UserService;

@Controller
@RequestMapping
public class WebUserController {
	
	private UserService userService;
	
	@Autowired
	public WebUserController(UserService userService) {
		this.userService = userService;
	}

	
	
}
