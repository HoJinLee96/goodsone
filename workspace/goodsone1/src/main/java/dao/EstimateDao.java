package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.EstimateDto;

@Repository
public class EstimateDao {

  DataSource dataSource;

  @Autowired
  public EstimateDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  public void registerEstimate(EstimateDto estimateDto) throws SQLException {
    String sql = "INSERT INTO estimate (name, phone, email, emailAgree, smsAgree, callAgree, postcode,mainAddress,detailAddress,content, imagesPath,status,created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,)";
    try(Connection con = dataSource.getConnection();
    PreparedStatement pst = con.prepareStatement(sql);){
      pst.setString(1, estimateDto.getName());
      pst.setString(2, estimateDto.getPhone());
      pst.setBoolean(3, estimateDto.isEmailAgree());
      pst.setBoolean(4, estimateDto.isSmsAgree());
      pst.setBoolean(5, estimateDto.isCallAgree());
      pst.setString(6, estimateDto.getPostcode());
      pst.setString(7, estimateDto.getMainAddress());
      pst.setString(8, estimateDto.getDetailAddress());
      pst.setString(9, estimateDto.getContent());
      pst.setString(10, estimateDto.getImagesPath());
      pst.setString(11, "접수");
      pst.setTimestamp(12, Timestamp.valueOf(LocalDateTime.now()));
      pst.executeUpdate();
    }
  }


}
