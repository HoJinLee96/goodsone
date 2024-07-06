package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.SmsResponseDto;

@Repository
public class VerificationRepositoryDao {

  private final DataSource dataSource;

  @Autowired
  public VerificationRepositoryDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public int sendSmsSave(SmsResponseDto responseDto) throws SQLException {
    String sql = "INSERT INTO verification (`to`,`confirm_num`,`status`,`create_at`) VALUES (?,?,?,?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, responseDto.getTo());
      statement.setString(2, responseDto.getSmsConfirmNum());
      statement.setString(3, responseDto.getStatusCode());
      statement.setTimestamp(4, Timestamp.valueOf(responseDto.getRequestTime()));
      int affectedRows = statement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("SmsResponseDto를 테이블에 저장했으나 바뀐 행 없음.");
      }

      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new SQLException("SmsResponseDto를 테이블에 저장했으나 시퀀스값 없음");
        }
      }
    }
  }

  public Optional<String> getConfirmNumber(int verSeq) throws SQLException {
    String sql = "SELECT `confirm_num` FROM verification where `ver_seq`=?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement state = con.prepareStatement(sql);) {
      state.setInt(1, verSeq);
      try (ResultSet rs = state.executeQuery()) {
        if (rs.next()) {
          return Optional.of(rs.getString("confirm_num"));
        }
      }
    }
    return Optional.empty();

  }
}
