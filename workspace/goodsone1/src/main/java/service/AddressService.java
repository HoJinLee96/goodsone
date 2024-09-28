package service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.AddressDao;
import dto.AddressDto;
import dto.UserDto;
import exception.NotFoundException;

@Service
public class AddressService {
  
  private AddressDao addressDao;

  @Autowired
  public AddressService(AddressDao addressDao) {
    this.addressDao = addressDao;
  }
  
  public void registerAddress(int userSeq, AddressDto addressDto) throws SQLException {
    addressDao.registerAddress(userSeq, addressDto);
  }
  
  public void updateAddress(AddressDto addressDto) throws SQLException{
    addressDao.updateAddress(addressDto);
  }
  
  public void deleteAddress(int addressSeq) throws SQLException{
    addressDao.deleteAddress(addressSeq);
  }
  
  public AddressDto getAddressDtoByAddressSeq(int addressSeq) throws SQLException, NotFoundException {
    return addressDao.getAddressDtoByAddressSeq(addressSeq).orElseThrow(()->new NotFoundException());
  }
  
  public List<AddressDto> getListByUserSeq(int userSeq) throws SQLException, NotFoundException {
    List<AddressDto> list = addressDao.getAddressListByUserSeq(userSeq);
    if (list.isEmpty()) {
        throw new NotFoundException("저장된 주소가 없습니다.");
    }
    return list;
  }
  
  public List<AddressDto> getSortedListByUserSeq(UserDto userDto) throws SQLException, NotFoundException {
    List<AddressDto> list = addressDao.getAddressListByUserSeq(userDto.getUserSeq());
    if (list.isEmpty()) {
        throw new NotFoundException("저장된 주소가 없습니다.");
    }
    for(AddressDto l : list) {
      if(userDto.getAddressSeq()==l.getAddressSeq()) {
        list.remove(l);
        list.addFirst(l);
      }
    }
    return list;
  }

}
