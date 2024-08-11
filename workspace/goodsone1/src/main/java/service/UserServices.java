package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.AddressDao;
import dao.OAuthDao;
import dao.UserDao;
import dto.AddressDto;
import dto.OAuthDto;
import dto.UserCredentials;
import dto.UserDto;
import dtoNaverLogin.OAuthToken;
import exception.NotFoundException;
import exception.NullDataException;

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
    
    public UserCredentials getPasswordByEmail(String email) throws NotFoundException, SQLException {
      Entry<Integer, String> opt = userDAO.getPasswordByEmail(email).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
      return new UserCredentials(opt.getKey(),email,opt.getValue());
    }
    
    public UserDto getUserBySeq(int userSeq) throws NotFoundException, SQLException {
    	return userDAO.getUserBySeq(userSeq).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }

    public UserDto getUserByEmail(String email) throws NotFoundException, SQLException {
        return userDAO.getUserByEmail(email).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));

    }

    public List<UserDto> getAllUsers() throws SQLException{
            return userDAO.getAllUsers();
    }

    @Transactional
    public void updateUser(UserDto user) throws SQLException{
            userDAO.updateUser(user);
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