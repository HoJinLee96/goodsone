package service;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.LoginLogDao;
import dto.User;

@Service
public class LoginLogService {

  LoginLogDao loginLogDao;
  
  @Autowired
  public LoginLogService(LoginLogDao loginLogDao) {
    this.loginLogDao = loginLogDao;
  }
  
  public int loginSuccess(User user,String ip) throws SQLException {
    return loginLogDao.loginSuccess(user, ip);
  }
  
  
}
