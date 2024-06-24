package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.WebControllerConfig;
import controller.WebMainController;

public class TestMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(WebControllerConfig.class);

		WebMainController service =
				ctx.getBean(WebMainController.class);
		System.out.println(service.getClass());
//		List<User> list = service.getAllUsers();
//		for (User u : list) {
//			System.out.println(u.getUserEmail());
//		}
	}
}
