package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.OAuthDto;
import exception.NotFoundException;

@Repository
public class OAuthDao {

  private final DataSource dataSource;

  @Autowired
  public OAuthDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // 유저등록
  public int registerOAuth(String provider, OAuthDto oAuthDto) throws SQLException {
    String sql =
        "INSERT INTO oauth (provider,id,email,name,birth,phone,created_at) VALUES(?,?,?,?,?,?,?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
      pst.setString(1, provider);
      pst.setString(2, oAuthDto.getId());
      pst.setString(3, oAuthDto.getEmail());
      pst.setString(4, oAuthDto.getName());
      pst.setString(5, oAuthDto.getBirth());
      pst.setString(6, oAuthDto.getPhone());
      pst.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
      pst.executeUpdate();
      ResultSet generatedKeys = pst.getGeneratedKeys();
      if (generatedKeys.next()) {
        return generatedKeys.getInt(1);
      } else
        throw new SQLException("서버 장애 발생.(키 생성되지 않음)");
    }
  }

  public Optional<OAuthDto> getOAuthByOAuthId(String provider, String oAuthid) throws SQLException, NotFoundException {
    String sql = "select * from oauth where provider=? and id=?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setString(1, provider);
      pst.setString(2, oAuthid);
      try (ResultSet rs = pst.executeQuery();) {
        if (rs.next()) {
          Integer userSeq = rs.getObject("user_seq", Integer.class);
          OAuthDto oAuthDto = new OAuthDto.Builder()
              .oauthSeq(rs.getInt("oauth_seq"))
              .userSeq(userSeq != null ? userSeq : 0) // userSeq가 null일 경우 0으로 설정
              .provider(rs.getString("provider"))
              .id(rs.getString("id"))
              .email(rs.getString("email"))
              .name(rs.getString("name"))
              .birth(rs.getString("birth"))
              .phone(rs.getString("phone"))
              .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
              .build();
      return Optional.ofNullable(oAuthDto);
        }
      }
    }
    return Optional.empty();
  }

  public Optional<OAuthDto> getOAuthByOAuthSeq(int oAuthSeq) throws SQLException {
    String sql = "select * from oauth where oauth_seq =?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setInt(1, oAuthSeq);
      try (ResultSet rs = pst.executeQuery();) {
        if (rs.next()) {
          Integer userSeq = rs.getObject("user_seq", Integer.class);
          OAuthDto oAuthDto = new OAuthDto.Builder()
              .oauthSeq(oAuthSeq)
              .userSeq(userSeq != null ? userSeq : 0) // userSeq가 null일 경우 0으로 설정
              .provider(rs.getString("provider"))
              .id(rs.getString("id"))
              .email(rs.getString("email"))
              .name(rs.getString("name"))
              .birth(rs.getString("birth"))
              .phone(rs.getString("phone"))
              .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
              .build();
      return Optional.ofNullable(oAuthDto);
        }
      }
    }
    return Optional.empty();
  }
  
  public int deleteOAuthDtoByOAuthId(String oAuthId) throws SQLException{
    String sql = "delete from oauth where id =?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setString(1, oAuthId);
      return pst.executeUpdate();
      }
  }

}
