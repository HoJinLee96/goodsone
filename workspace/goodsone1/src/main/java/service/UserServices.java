package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.UserDao;
import dto.User;
import dto.UserCredentials;
import dto.UserRegister;
import exception.UserNotFoundException;

@Service
public class UserServices {
    private final UserDao userDAO;

    @Autowired
    public UserServices(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void registerUser(UserRegister userRegister) throws SQLException {
            int i = userDAO.registerUser(userRegister);
            if(i!=1) throw new SQLException();
        }
    
    public String getPasswordBySeq(int userSeq) throws UserNotFoundException, SQLException {
      return userDAO.getPasswordBySeq(userSeq).orElseThrow(()-> new UserNotFoundException("일치하는 회원이 없습니다."));
    }
    
    public UserCredentials getPasswordByEmail(String email) throws UserNotFoundException, SQLException {
      Entry<Integer, String> opt = userDAO.getPasswordByEmail(email).orElseThrow(()-> new UserNotFoundException("일치하는 회원이 없습니다."));
      return new UserCredentials(opt.getKey(),email,opt.getValue());

    }
    
    public User getUserBySeq(int userSeq) throws UserNotFoundException, SQLException {
    	return userDAO.getUserBySeq(userSeq).orElseThrow(()-> new UserNotFoundException("일치하는 회원이 없습니다."));
    }

    public User getUserByEmail(String email) throws UserNotFoundException, SQLException {
        return userDAO.getUserByEmail(email).orElseThrow(()-> new UserNotFoundException("일치하는 회원이 없습니다."));

    }

    public List<User> getAllUsers() throws SQLException{
            return userDAO.getAllUsers();
    }

    @Transactional
    public void updateUser(User user) {
        try {
            userDAO.updateUser(user);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Transactional
    public void deleteUser(int userSeq) {
        try {
            userDAO.deleteUser(userSeq);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to delete user", e);
        }
    }
}