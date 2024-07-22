package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.AddressDto;
import dto.UserDto;

@Repository
public class AddressDao {
  private final DataSource dataSource;

  @Autowired
  public AddressDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  // **** 주소등록 ****
  public void registerAddress(int userSeq, AddressDto addressDto) throws SQLException {
    String sql =
        "INSERT INTO address (user_seq, postcode, main_address, detail_address) VALUES (?, ?, ?, ?)";
    try (Connection connection = dataSource.getConnection();
        PreparedStatement addressStatement = connection.prepareStatement(sql);) {
      addressStatement.setInt(1, userSeq);
      addressStatement.setInt(2, addressDto.getPostcode());
      addressStatement.setString(3, addressDto.getMainAddress());
      addressStatement.setString(4, addressDto.getDetailAddress());
      addressStatement.executeUpdate();

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
