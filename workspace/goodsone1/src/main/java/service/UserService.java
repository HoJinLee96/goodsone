package service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import dto.User;
import exception.UserNotFoundException;

@Service
public class UserService {
    private final UserDao userDAO;

    @Autowired
    public UserService(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void addUser(User user) {
        try {
            userDAO.addUser(user);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to add user", e);
        }
    }
    
    public User getUserBySeq(int userSeq) throws UserNotFoundException, SQLException {
    	return userDAO.getUser(userSeq).orElseThrow(()-> new UserNotFoundException("일치하는 회원이 없습니다."));
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