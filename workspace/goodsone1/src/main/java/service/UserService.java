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
    
    public boolean comparePasswordBySeq(int userSeq, String reqPassword) throws SQLException {
      // 혹여나 특정 계정을 유추 할수 있을까봐 NotFoundException 삭제
      String password = userDAO.getPasswordBySeq(userSeq).orElse("");
      return passwordEncoder.matches(reqPassword, password);
    }
    
    public boolean comparePasswordByEmail(String email,String reqPassword) throws SQLException {
      // 혹여나 특정 계정을 유추 할수 있을까봐 NotFoundException 삭제
      String password = userDAO.getPasswordByEmail(email).orElse("");
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
      return userDAO.updatePassword(userSeq,newEncodePassword).orElseThrow(()-> new SQLException("비밀번호 변경 오류 발생."));
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