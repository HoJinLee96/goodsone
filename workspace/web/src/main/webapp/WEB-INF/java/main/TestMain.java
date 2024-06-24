package main;

import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.DataSourceConfig;
import dao.UserDAOImpl;
import dto.User;

public class TestMain {
	public static void main(String[] args) throws SQLException {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(DataSourceConfig.class);
		
		UserDAOImpl userDao = ctx.getBean(UserDAOImpl.class);
		
		
		List<User> list = userDao.getAllUsers();
		for(User user : list) {
			System.out.println(user.getUserSeq()+ " " + user.getUserEmail() +" "+ user.getUserPassword()+" "+ user.getUserName()+" "+ user.getCreatedAt());
		}
	}
}
