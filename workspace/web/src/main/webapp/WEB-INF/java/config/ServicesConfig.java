package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"core.dao","core.service"})
//@Import(DataSourceConfig.class)
public class ServicesConfig {

	
//	@Component
//	@Bean
//	public UserDAOImpl memberDao() {
//		return new UserDAOImpl (dataSource());
//	}

//	@Component
//	@Bean
//	public UserServiceImpl userServiceImpl() {
//		return new UserServiceImpl(userDaoImpl());
//	}
}
