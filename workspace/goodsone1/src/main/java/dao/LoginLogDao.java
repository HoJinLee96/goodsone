package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import dto.User;

@Repository
public class LoginLogDao {

  private final DataSource dataSource;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public LoginLogDao(DataSource dataSource,JdbcTemplate jdbcTemplate) {
    this.dataSource = dataSource;
    this.jdbcTemplate = jdbcTemplate;
  }
  
  public int loginSuccess(User user,String ip) throws SQLException {

    String sql = "insert into login_success_log (provider,id,ip) values (?,?,?);";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
      pst.setString(1, user.getProvider());
      pst.setString(2, user.getEmail());
      pst.setString(3, ip);
      pst.executeUpdate();
      ResultSet generatedKeys = pst.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      }else
        throw new SQLException("서버 장애 발생.");
      }
    }
  
//public int loginFail(User user,String reason) throws SQLException {
//  }
  
}
