package service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.AddressDao;
import dto.AddressDto;

@Service
public class AddressServices {
  
  private AddressDao addressDao;

  @Autowired
  public AddressServices(AddressDao addressDao) {
    this.addressDao = addressDao;
  }
  
  public List<AddressDto> getListByUserSeq(int userSeq) throws SQLException {
    List<AddressDto> list = addressDao.getAddressListByUserSeq(userSeq);
    if (list.isEmpty()) {
        throw new SQLException("저장된 주소가 없습니다.");
    }
    return list;
}

}
