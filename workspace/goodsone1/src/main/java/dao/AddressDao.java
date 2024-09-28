package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dto.AddressDto;

@Repository
public class AddressDao {
  private final DataSource dataSource;

  @Autowired
  public AddressDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // **** 주소등록 ****
  @Transactional
  public void registerAddress(int userSeq, AddressDto addressDto) throws SQLException {
    String sql =
        "INSERT INTO address (user_seq, name, postcode, main_address, detail_address,updated_at) VALUES (?, ?, ?, ?, ?,?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ) {
      pst.setInt(1, userSeq);
      pst.setString(2, addressDto.getName());
      pst.setInt(3, addressDto.getPostcode());
      pst.setString(4, addressDto.getMainAddress());
      pst.setString(5, addressDto.getDetailAddress());
      pst.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
      pst.executeUpdate();
      ResultSet generatedKeys = pst.getGeneratedKeys();
      if (generatedKeys.next()) {
        sql = "update `user` set `address_seq` = ? where `user_seq` = ?";
        int addressSeq = generatedKeys.getInt(1);
        try(PreparedStatement updatePst = con.prepareStatement(sql);){
          updatePst.setInt(1, addressSeq);
          updatePst.setInt(2, userSeq);
          updatePst.executeUpdate();
        }
      }
    }
  }
  
  @Transactional
  public void updateAddress(AddressDto addressDto) throws SQLException {
    String sql =
        "update `address` set `name` = ?, `postcode` = ?, `main_address` = ?,  `detail_address` = ? where `address_seq` = ?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ) {
      pst.setString(1, addressDto.getName());
      pst.setInt(2, addressDto.getPostcode());
      pst.setString(3, addressDto.getMainAddress());
      pst.setString(4, addressDto.getDetailAddress());
      pst.setInt(5, addressDto.getAddressSeq());
      pst.executeUpdate();

    }
  }
  
  @Transactional
  public void deleteAddress(int addressSeq) throws SQLException {
    String sql =
        "delete from `address` where `address_seq` = ?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ) {
      pst.setInt(1, addressSeq);
      pst.executeUpdate();

    }
  }
  
  public Optional<AddressDto> getAddressDtoByAddressSeq(int addressSeq) throws SQLException {
    String sql = "select * from address where address_seq=?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
        pst.setInt(1, addressSeq);
        try (ResultSet rs = pst.executeQuery()) {
          if (rs.next()) {
            AddressDto addressDto = new AddressDto(
                addressSeq, 
                rs.getInt("user_seq"),
                rs.getString("name"), 
                rs.getInt("postcode"),
                rs.getString("main_address"), 
                rs.getString("detail_address"), 
                rs.getTimestamp("created_at").toLocalDateTime(), 
                rs.getTimestamp("updated_at").toLocalDateTime()
                );
            return Optional.of(addressDto);
          }
      }
      return Optional.empty();
    }
  }

  public List<AddressDto> getAddressListByUserSeq(int userSeq) throws SQLException {
    List<AddressDto> list = new ArrayList<>();
    String sql = "SELECT * FROM address where user_seq= ? ORDER BY updated_at DESC";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);) {
      pst.setInt(1, userSeq);
      try (ResultSet rs = pst.executeQuery()) {
        while (rs.next()) {
          list.add(new AddressDto(
              rs.getInt("address_seq"),
              userSeq,
              rs.getString("name"), 
              rs.getInt("postcode"),
              rs.getString("main_address"), 
              rs.getString("detail_address"), 
              rs.getTimestamp("created_at").toLocalDateTime(), 
              rs.getTimestamp("updated_at").toLocalDateTime()
              ));
        }
      }
    }
    return list;
  }



}
