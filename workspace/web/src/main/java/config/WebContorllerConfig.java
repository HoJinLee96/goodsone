package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages="web.controller")
//@Import(ServicesConfig.class)
public class WebContorllerConfig {
	
//	@Component
//	@Bean
//	public WebMainController webMainController() {
//		return new WebMainController(userService());
//	}
	
//	@Component
//	@Bean
//	public WebUserController webUserController() {
//		return new WebUserController(userService());
//	}

}