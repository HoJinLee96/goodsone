package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages= {"dao"})
@EnableTransactionManagement //트랜잭션
public class DataSourceConfig {

	@Bean(destroyMethod ="close")
	public DataSource dataSource() {
		DataSource ds = new DataSource(); 
		ds.setDriverClassName("com.mysql.jdbc.Driver"); //JDBC 드라이버 클래스를 MySQL 드라이버 클래스로 지정.
		ds.setUrl("jdbc:mysql://localhost/goodsone1?characterEncoding=utf8"); //JDBC URL 지정 //?useSSL=no
		ds.setUsername("root");
		ds.setPassword("00000000");
		ds.setInitialSize(10);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(1000*60*3);
		ds.setTimeBetweenEvictionRunsMillis(1000*10);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManage() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}

}
