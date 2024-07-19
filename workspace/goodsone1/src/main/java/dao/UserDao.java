package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import dto.UserDto;

@Repository
public class UserDao {
  private final DataSource dataSource;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserDao(DataSource dataSource,JdbcTemplate jdbcTemplate) {
    this.dataSource = dataSource;
    this.jdbcTemplate = jdbcTemplate;
  }


  // **** 회원가입 ****
  public int registerUser(UserDto userDto) throws SQLException {
    String sql =
        "INSERT INTO user (email, password, name, nickname, birth, phone, status, marketing_received_status, tier_seq, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

      pst.setString(1, userDto.getEmail());
      pst.setString(2, userDto.getPassword());
      pst.setString(3, userDto.getName());
      pst.setString(4, userDto.getName());
      pst.setString(5, userDto.getBirth());
      pst.setString(6, userDto.getPhone());
      pst.setString(7, "public");
      pst.setBoolean(8, true);;
      pst.setString(9, "일반");
      pst.setTimestamp(10, Timestamp.valueOf(userDto.getCreatedAt()));
      pst.executeUpdate();
      ResultSet generatedKeys = pst.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      }else
        throw new SQLException("서버 장애 발생.");
    }
  }

  public Optional<UserDto> getUserBySeq(int userSeq) throws SQLException {
    String sql = "SELECT * FROM user WHERE user_seq = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, userSeq);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {


          return Optional.of(null);
        }
      }
    }
    return Optional.empty();
  }

  public Optional<UserDto> getUserByEmail(String email) throws SQLException {
    String sql = "SELECT * FROM user WHERE user_email = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, email);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {



          return Optional.of(null);
        }
      }
    }
    return Optional.empty();
  }

  public Optional<String> getPasswordBySeq(int seq) throws SQLException {
    String sql = "SELECT password FROM user WHERE user_seq = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, seq);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(resultSet.getString("user_password"));
        }
      }
    }
    return Optional.empty();
  }

  public Optional<Map.Entry<Integer, String>> getPasswordByEmail(String email) throws SQLException {
    String sql = "SELECT user_seq, password FROM user WHERE email = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, email);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          int userSeq = resultSet.getInt("user_seq");
          String userPassword = resultSet.getString("user_password");
          return Optional.of(new AbstractMap.SimpleEntry<>(userSeq, userPassword));
        }
      }
    }
    return Optional.empty();
  }

  public List<UserDto> getAllUsers() throws SQLException {
    List<UserDto> users = new ArrayList<>();
    String sql = "SELECT * FROM user";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery()) {

      while (resultSet.next()) {

        users.add(null);
      }
    }
    return users;
  }

  // Update
  public void updateUser(UserDto user) throws SQLException {
    String sql =
        "UPDATE user SET user_email = ?, user_oldpassword = ?, user_name = ?, user_nickname = ?, user_birth = ?, user_phone_agency = ?, user_phone_number = ?, user_address = ?, updated_at = ?, user_status = ?, user_signtype = ? WHERE user_seq = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

    }
  }

  public void deleteUser(int userSeq) throws SQLException {
    String sql = "DELETE FROM user WHERE user_seq = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, userSeq);
      statement.executeUpdate();
    }
  }
  
  public boolean isEmailExists(String email) throws SQLException{
    String sql = "SELECT COUNT(*) FROM `user` WHERE `email` = ?";
    int count = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
    return count > 0;
  }
  public boolean isPhoneExists(String phone) throws SQLException{
    String sql = "SELECT COUNT(*) FROM `user` WHERE `phone` = ?";
    int count = jdbcTemplate.queryForObject(sql, new Object[]{phone}, Integer.class);
    return count > 0;
  }
}
