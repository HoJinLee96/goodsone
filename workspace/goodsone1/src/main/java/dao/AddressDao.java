package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        "INSERT INTO address (user_seq, postcode, main_address, detail_address) VALUES (?, ?, ?, ?)";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ) {
      pst.setInt(1, userSeq);
      pst.setInt(2, addressDto.getPostcode());
      pst.setString(3, addressDto.getMainAddress());
      pst.setString(4, addressDto.getDetailAddress());
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
  public void updateAddress(int addressSeq, AddressDto addressDto) throws SQLException {
    String sql =
        "update `address` set `postcode` = ?, `main_address` = ?,  `detail_address` = ? where `address_seq` = ?";
    try (Connection con = dataSource.getConnection();
        PreparedStatement pst = con.prepareStatement(sql);
        ) {
      pst.setInt(1, addressDto.getPostcode());
      pst.setString(2, addressDto.getMainAddress());
      pst.setString(3, addressDto.getDetailAddress());
      pst.setInt(4, addressSeq);
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

  public List<AddressDto> getAddressListByUserSeq(int userSeq) throws SQLException {
    List<AddressDto> list = new ArrayList<>();
    String sql = "SELECT * FROM address where user_seq= ?";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      statement.setInt(1, userSeq);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          list.add(new AddressDto(resultSet.getInt(1), userSeq, resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)));
        }
      }
    }
    return list;
  }



}
