package service;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dao.VerificationDao;
import dto.VerifyResponseDto;

@Service
public class VerificationServices {
  
  VerificationDao verificationDao;
  
  @Autowired
  VerificationServices(VerificationDao verificationDao){
    this.verificationDao = verificationDao;
  }
  
  public boolean compareCode(String seq,String reqCode) throws SQLException{
    String verificationCode = verificationDao.getVerificationCode(Integer.valueOf(seq)).orElseThrow(()->new SQLException("잠시후 시도 해주세요."));
    return verificationCode.equals(reqCode);
  }
  
  public int register(VerifyResponseDto responseDto) throws SQLException {
    return verificationDao.register(responseDto);
  }
}
