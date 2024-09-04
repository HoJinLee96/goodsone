package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import util.HttpUtil;

@Configuration
public class EtcConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
      int strength = 12;
      return new BCryptPasswordEncoder(strength);
  }
  
  @Bean
  public HttpUtil httpUtil() {
    return new HttpUtil();
  }
  
}
