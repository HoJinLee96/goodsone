package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.DataSourceConfig;

public class TestMain {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(DataSourceConfig.class);
		
		}
	}

