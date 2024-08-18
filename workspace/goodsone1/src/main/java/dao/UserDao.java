package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
  
  public int registerUser(UserDto userDto) throws SQLException {
    String sql =
        "INSERT INTO user (email, password, name, birth, phone, status, marketing_received_status, created_at) VALUES (?,?,?, ?, ?, ?, ?, ?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
      pst.setString(1, userDto.getEmail());
      pst.setString(2, userDto.getPassword());
      pst.setString(3, userDto.getName());
      pst.setString(4, userDto.getBirth());
      pst.setString(5, userDto.getPhone());
      pst.setString(6, "NORMAL");
      pst.setBoolean(7, userDto.isMarketingReceivedStatus());;
      pst.setTimestamp(8,Timestamp.valueOf(LocalDateTime.now()));
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
                UserDto userDto = new UserDto.Builder()
                    .userSeq(resultSet.getInt("user_seq"))
                    .email(resultSet.getString("email"))
                    // .password(resultSet.getString("password")) // 패스워드는 설정하지 않음
                    .name(resultSet.getString("name"))
                    .birth(resultSet.getString("birth"))
                    .phone(resultSet.getString("phone"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(
                        resultSet.getTimestamp("updated_at") != null ? 
                        resultSet.getTimestamp("updated_at").toLocalDateTime() : 
                        null
                    )
                    .status(UserDto.Status.valueOf(resultSet.getString("status"))) // 수정된 부분
                    .marketingReceivedStatus(resultSet.getBoolean("marketing_received_status"))
                    .build();

                return Optional.ofNullable(userDto);
            }
        }
    }
    return Optional.empty();
}

  public Optional<UserDto> getUserByEmail(String email) throws SQLException {
    String sql = "SELECT * FROM user WHERE email = ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, email);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          UserDto userDto = new UserDto.Builder()
              .userSeq(resultSet.getInt("user_seq"))
              .email(resultSet.getString("email"))
              // .password(resultSet.getString("password")) // 패스워드는 설정하지 않음
              .name(resultSet.getString("name"))
              .birth(resultSet.getString("birth"))
              .phone(resultSet.getString("phone"))
              .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
              .updatedAt(
                  resultSet.getTimestamp("updated_at") != null ? 
                  resultSet.getTimestamp("updated_at").toLocalDateTime() : 
                  null
              )
              .status(UserDto.Status.valueOf(resultSet.getString("status"))) 
              .marketingReceivedStatus(resultSet.getBoolean("marketing_received_status"))
              .build();

          return Optional.ofNullable(userDto);
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
          String password = resultSet.getString("password");
          return Optional.of(new AbstractMap.SimpleEntry<>(userSeq, password));
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
          UserDto userDto = new UserDto.Builder()
              .userSeq(resultSet.getInt("user_seq"))
              .email(resultSet.getString("email"))
              // .password(resultSet.getString("password")) // 패스워드는 설정하지 않음
              .name(resultSet.getString("name"))
              .birth(resultSet.getString("birth"))
              .phone(resultSet.getString("phone"))
              .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
              .updatedAt(
                  resultSet.getTimestamp("updated_at") != null ? 
                  resultSet.getTimestamp("updated_at").toLocalDateTime() : 
                  null
              )
              .status(UserDto.Status.valueOf(resultSet.getString("status"))) // 수정된 부분
              .marketingReceivedStatus(resultSet.getBoolean("marketing_received_status"))
              .build();

        users.add(userDto);
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
