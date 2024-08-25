package trash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class UserCredentials {
  private final int userSeq;
  private final String userEmail;
  private final String hashedPassword;
  private final byte[] salt;
  

  public UserCredentials(int userSeq, String userEmail ,String userPassword) {
      this.userSeq = userSeq;
      this.userEmail = userEmail;
      this.salt=generateSalt();
      this.hashedPassword = hashPassword(userPassword); 
      
      }

  public int getUserSeq() {
      return userSeq;
  }
  
  public String getUserEmail() {
    return userEmail;
}

  public String getHashedPassword() {
      return hashedPassword;
  }
  
  private String hashPassword(String password) {
    try {
        // 비밀번호와 솔트를 합쳐서 해싱
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt);
        byte[] hashedBytes = md.digest(password.getBytes());
        // 솔트와 해시된 비밀번호를 Base64로 인코딩하여 반환
        return Base64.getEncoder().encodeToString(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("SHA-256 algorithm not found", e);
    }
}

  
  // 입력된 비밀번호를 해싱된 비밀번호와 비교
  public boolean validatePassword(String reqPassword) {
      try {
          // 저장된 hashedPasswordf를 디코딩
          byte[] storedPasswordHash = Base64.getDecoder().decode(hashedPassword);

          // 입력된 비밀번호를 동일한 솔트를 사용하여 해싱
          MessageDigest md = MessageDigest.getInstance("SHA-256");
          md.update(salt);
          byte[] hashedBytes = md.digest(reqPassword.getBytes());
          
          
          System.out.println(MessageDigest.isEqual(storedPasswordHash, hashedBytes));
          // 해시된 비밀번호를 저장된 해시와 비교
          return MessageDigest.isEqual(storedPasswordHash, hashedBytes);
          
      } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException("SHA-256 algorithm not found", e);
      }
  }
  
  // 솔트 생성 메서드
  private byte[] generateSalt() {
      SecureRandom random = new SecureRandom();
      byte[] salt = new byte[16];
      random.nextBytes(salt);
      return salt;
  }
}
