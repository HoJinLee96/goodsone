package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.AddressDao;
import dao.UserDao;
import dto.AddressDto;
import dto.UserDto;
import exception.NotFoundException;

@Service
public class UserServices {
    private final UserDao userDAO;
    private final AddressDao addressDao;

    @Autowired
    public UserServices(UserDao userDAO,AddressDao addressDao) {
        this.userDAO = userDAO;
        this.addressDao = addressDao;
    }

    @Transactional
    public int registerUser(UserDto userDto,AddressDto addressDto) throws SQLException {
    int userSeq = userDAO.registerUser(userDto);
    addressDao.registerAddress(userSeq, addressDto);
      return userSeq;
        }
    
    public String getPasswordBySeq(int userSeq) throws NotFoundException, SQLException {
      return userDAO.getPasswordBySeq(userSeq).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }
    
    public String getPasswordByEmail(String email) throws NotFoundException, SQLException {
      return userDAO.getPasswordByEmail(email).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }
    
    public UserDto getUserBySeq(int userSeq) throws NotFoundException, SQLException {
    	return userDAO.getUserBySeq(userSeq).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }

    public UserDto getUserByEmail(String email) throws NotFoundException, SQLException {
        return userDAO.getUserByEmail(email).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));

    }
    
    public String getEmailByPhone(String phone) throws NotFoundException, SQLException {
      return userDAO.getEmailByPhone(phone).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));

  }

    public List<UserDto> getAllUsers() throws SQLException{
            return userDAO.getAllUsers();
    }

    @Transactional
    public Integer updateUser(UserDto user) throws NotFoundException, SQLException{
      return userDAO.updateUser(user).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }

    @Transactional
    public void deleteUser(int userSeq) throws SQLException {
            userDAO.deleteUser(userSeq);
    }
    
    //user 테이블 이메일 중복 검사
    public boolean isEmailExists(String email) throws SQLException {
      return userDAO.isEmailExists(email);
    }
    //user 테이블 휴대폰번호 중복 검사
    public boolean isPhoneExists(String phone) throws SQLException {
      return userDAO.isPhoneExists(phone);
    }
    

}