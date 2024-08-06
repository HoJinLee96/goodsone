package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.EstimateDto;
import dto.EstimateDto.Status;

@Repository
public class EstimateDao {

  DataSource dataSource;

  @Autowired
  public EstimateDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void registerEstimate(EstimateDto estimateDto) throws SQLException {
    String sql =
        "INSERT INTO estimate (name, phone, email, emailAgree, smsAgree, callAgree, postcode,mainAddress,detailAddress,content, imagesPath,status,created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setString(1, estimateDto.getName());
      pst.setString(2, estimateDto.getPhone());
      pst.setString(3, estimateDto.getEmail());
      pst.setBoolean(4, estimateDto.isEmailAgree());
      pst.setBoolean(5, estimateDto.isSmsAgree());
      pst.setBoolean(6, estimateDto.isCallAgree());
      pst.setString(7, estimateDto.getPostcode());
      pst.setString(8, estimateDto.getMainAddress());
      pst.setString(9, estimateDto.getDetailAddress());
      pst.setString(10, estimateDto.getContent());
      pst.setString(11, estimateDto.getImagesPath());
      pst.setString(12, estimateDto.getStatus().name());
      pst.setTimestamp(13, Timestamp.valueOf(LocalDateTime.now()));
      pst.executeUpdate();
    }
  }
  
  public int getCountAll() throws SQLException {
    System.out.println("EstimateDao.getCountAll() 실행");

    String sql = "SELECT COUNT(*) FROM estimate";
    try (Connection con = dataSource.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1); 
        }
    }
    return 0; 
}
  

  public String getImagesPath(int estimateSeq) throws SQLException {
    System.out.println("EstimateDao.getImagesPath() 실행");

    String sql = "SELECT imagesPath FROM estimate WHERE estimate_seq = ?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setInt(1, estimateSeq);
      try (ResultSet rs = pst.executeQuery()) {
        if (rs.next()) {
          return rs.getString("imagesPath");
        }
      }
    }
    return null;
  }

  public List<EstimateDto> getAllEstimate(int page) throws SQLException {
    System.out.println("EstimateDao.getAllEstimate() 실행");

    String sql = "SELECT * FROM estimate ORDER BY created_at DESC LIMIT 50 OFFSET " + (page-1) * 50;
    System.out.println(sql);
    List<EstimateDto> estimates = new ArrayList<>();

    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();) {
      estimates = mapResultSetToEstimates(rs);
    }

    return estimates;
  }

  public List<EstimateDto> getEstimateByStatus(int chepter, Status status) throws SQLException {
    System.out.println("EstimateDao.getEstimateByStatus() 실행");

    String sql = "SELECT * FROM estimate WHERE status = ? ORDER BY created_at DESC LIMIT 50 OFFSET "
        + chepter * 50;
    List<EstimateDto> estimates = new ArrayList<>();

    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql)) {
      pst.setString(1, status.name());
      try (ResultSet rs = pst.executeQuery()) {
        estimates = mapResultSetToEstimates(rs);
      }
    }

    return estimates;
  }


  public List<EstimateDto> mapResultSetToEstimates(ResultSet rs) throws SQLException {
    System.out.println("EstimateDao.mapResultSetToEstimates() 실행");
    List<EstimateDto> estimates = new ArrayList<>();
    while (rs.next()) {
      EstimateDto estimate = new EstimateDto();
      estimate.setEstimateSeq(rs.getInt("estimate_seq"));
      estimate.setName(rs.getString("name"));
      estimate.setPhone(rs.getString("phone"));
      estimate.setEmail(rs.getString("email"));
      estimate.setEmailAgree(rs.getBoolean("emailAgree"));
      estimate.setSmsAgree(rs.getBoolean("smsAgree"));
      estimate.setCallAgree(rs.getBoolean("callAgree"));
      estimate.setPostcode(rs.getString("postcode"));
      estimate.setMainAddress(rs.getString("mainAddress"));
      estimate.setDetailAddress(rs.getString("detailAddress"));
      estimate.setContent(rs.getString("content"));
      estimate.setImagesPath(rs.getString("imagesPath"));
      estimate.setStatus(Status.valueOf(rs.getString("status")));
      estimate.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      System.out.println(rs.getTimestamp("created_at").toLocalDateTime());
      estimates.add(estimate);
    }
    return estimates;
  }



}
