package main;

import java.sql.SQLException;
import java.util.Optional;
import dao.UserDao;
import dto.UserDto;

public class TestMain {
  UserDao userDao;
  
  public static void main(String[] args) {
    
  }

public void printUserEmail(String email) throws SQLException {
  Optional<UserDto> userOptional = userDao.getUserByEmail(email);
  
  Integer userSeq = userOptional.map(UserDto::getUserSeq).orElse(new Integer(1)); // 이메일이 없을 경우 기본 이메일 반환; 
  // User가 존재하면 이메일을 추출
}
}