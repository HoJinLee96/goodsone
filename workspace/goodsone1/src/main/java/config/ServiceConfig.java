package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages={"dao","service"})
public class ServiceConfig {
	
//	@Bean
//	public UserDaoImpl userDaoImpl() {
//		return new UserDaoImpl(dataSource());
//	}
//
//	@Bean
//	public UserServiceImpl userServiceImpl() {
//		return new UserServiceImpl();
//	}
}
