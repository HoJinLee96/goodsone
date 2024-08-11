package service;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.OAuthDao;
import dto.OAuthDto;
import exception.NotFoundException;

@Service
public class OAuthService {
  
  
  private OAuthDao oAuthDao;

  @Autowired
  public OAuthService(OAuthDao oAuthDao) {
    this.oAuthDao = oAuthDao;
  }

  //OAuth
  //1. 가입 및 개인 계정이 연결되어 있을때 OAuth 반환
  //2. 가입되어 있지 않을때 NotFoundException 반환
  //3. sql 구문 및 DB기타 오류 발생시 SQLException 반환
  public OAuthDto getOAuthByOAuthId(String provider,String oAuthid) throws SQLException, NotFoundException {
    return oAuthDao.getOAuthByOAuthId(provider, oAuthid).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
  }
  
  public OAuthDto getOAuthByOAuthSeq(int oAuthSeq) throws SQLException, NotFoundException {
    return oAuthDao.getOAuthByOAuthSeq(oAuthSeq).orElseThrow(()-> new NotFoundException("일치하는 회원이 없습니다."));
  }
  
  // 계정 등록
  @Transactional
  public void registerOAuth(String provider, OAuthDto oAuthDto) throws SQLException,NotFoundException {
    int result = oAuthDao.registerOAuth(provider,oAuthDto);
    if (result == 0) throw new NotFoundException();
  }
  
  // 데이터 완전 삭제
//  @Transactional
//  public void deleteOAuthDtoByOAuthId(String oAuthId) throws SQLException, NotFoundException {
//    int result = oAuthDao.deleteOAuthDtoByOAuthId(oAuthId);
//    if (result == 0) throw new NotFoundException();
//  }
  
  // 회원 탈퇴
  @Transactional
  public void stopOAuthDtoByOAuthId(String oAuthId) throws SQLException, NotFoundException {
    int result = oAuthDao.stopOAuthDtoByOAuthId(oAuthId);
    if (result == 0) throw new NotFoundException();
  }
}