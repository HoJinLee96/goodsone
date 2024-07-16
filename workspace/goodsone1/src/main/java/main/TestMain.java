package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import config.DataSourceConfig;

public class TestMain {
  public static void main(String[] args) {

    AnnotationConfigApplicationContext apx =
        new AnnotationConfigApplicationContext(DataSourceConfig.class);
    DataSource ds = apx.getBean("dataSource", DataSource.class);
    String sql1 = "CREATE TABLE test1 (USER_ID SERIAL PRIMARY KEY,CONTENT VARCHAR(255) DEFAULT NULL)";
    String sql2 = "INSERT INTO test1(CONTENT) values ('테스트')";
    String sql3 = "select * from chamcham_spring_webservice.test2";

    try (Connection cn = ds.getConnection(); PreparedStatement pst = cn.prepareStatement(sql3)) {
      ResultSet rs  = pst.executeQuery();
      if(rs.next())
        System.out.println(rs.getString(1)+rs.getString(2));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }



}
