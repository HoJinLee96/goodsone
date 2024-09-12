package service;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.AddressDao;
import dao.UserDao;
import dto.AddressDto;
import dto.UserDto;
import exception.NotFoundException;

@Service
public class UserService {
    private final UserDao userDAO;
    private final AddressDao addressDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDAO,AddressDao addressDao,PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.addressDao = addressDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public int registerUser(UserDto userDto,AddressDto addressDto) throws SQLException {
      String encodePassoword = passwordEncoder.encode(userDto.getPassword());
      int userSeq = userDAO.registerUser(userDto, encodePassoword);
      addressDao.registerAddress(userSeq, addressDto);
      return userSeq;
    }
    
    public boolean comparePasswordBySeq(int userSeq, String reqPassword) throws SQLException, NotFoundException {
      String password = userDAO.getPasswordBySeq(userSeq).orElseThrow(()->new NotFoundException());
      return passwordEncoder.matches(reqPassword, password);
    }
    
    public boolean comparePasswordByEmail(String email,String reqPassword) throws SQLException, NotFoundException {
      String password = userDAO.getPasswordByEmail(email).orElseThrow(()->new NotFoundException());
      return passwordEncoder.matches(reqPassword, password);
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

//    @Transactional
//    public Integer updateUser(UserDto user) throws NotFoundException, SQLException{
//      return userDAO.updateUser(user).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
//    }

    @Transactional
    public Integer updatePassword(int userSeq,String newPassword) throws NotFoundException, SQLException{
      String newEncodePassword = passwordEncoder.encode(newPassword);
      return userDAO.updatePassword(userSeq,newEncodePassword).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
    }

    @Transactional
    public void deleteUser(int userSeq) throws SQLException {
            userDAO.deleteUser(userSeq);
    }
    
    public String getUserStatusByEmail(String email) throws NotFoundException {
      return userDAO.getUserStatusByEmail(email);
    }
    
    public boolean isEmailExists(String email) {
      return userDAO.isEmailExists(email);
    }
    
    public boolean isPhoneExists(String phone) {
      return userDAO.isPhoneExists(phone);
    }
    
    public boolean isEmailPhoneExists(String email, String phone){
      return userDAO.isEmailPhoneExists(email, phone);
    }
    
    public int countLoginFail(String id) {
      return userDAO.countLoginFail(id);
    }

}