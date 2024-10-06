package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  
  @Transactional
  public void registerAddress(int userSeq, AddressDto addressDto) throws SQLException {
    addressDao.registerAddress(userSeq, addressDto);
  }
  
  @Transactional
  public void updateAddress(AddressDto addressDto) throws SQLException,NotFoundException{
    int result = addressDao.updateAddress(addressDto);
    if(result==0) throw new NotFoundException();
  }
  
  @Transactional
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
    List<AddressDto> copyList = new ArrayList<>(list);
    for (AddressDto l : copyList) {
        if (userDto.getAddressSeq() == l.getAddressSeq()) {
            list.remove(l); // 원본 리스트에서 수정
            list.addFirst(l);
        }
    }
    return list;
  }

}
