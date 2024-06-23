package main;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.DataSourceConfig;
import dao.UserDaoImpl;
import dto.User;

public class TestMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(DataSourceConfig.class);
		
		UserDaoImpl userDao = ctx.getBean(UserDaoImpl.class);
		
		
		List<User> list = userDao.selectAll();
		for(User user : list) {
			System.out.println(user.getUserId() +" "+ user.getUserPassword()+" "+ user.getUserName()+" "+ user.getCreatedAt());
		}
	}
}
