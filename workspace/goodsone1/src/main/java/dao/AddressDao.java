package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dto.AddressDto;

@Repository
public class AddressDao {
  private final DataSource dataSource;

  @Autowired
  public AddressDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }
  
  // **** 주소등록 ****
  public void registerAddress(int userSeq,AddressDto addressDto) throws SQLException {
    String sql =
        "INSERT INTO address (user_seq, postcode, main_address, detail_address) VALUES (?, ?, ?, ?)";
    try(Connection connection = dataSource.getConnection();
        PreparedStatement addressStatement = connection.prepareStatement(sql);){
      addressStatement.setInt(1, userSeq);
      addressStatement.setInt(2, addressDto.getPostcode());
      addressStatement.setString(3, addressDto.getMainAddress());
      addressStatement.setString(4, addressDto.getDetailAddress());
      addressStatement.executeUpdate();

    }
  }

  
  
}
