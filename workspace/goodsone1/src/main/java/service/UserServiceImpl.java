package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import dto.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDAO;

    @Autowired
    public UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
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
    
    @Override
    public Optional<User> getUser(int userSeq) {
        try {
            return userDAO.getUser(userSeq);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to get user", e);
        }
    }
    
    @Override
    public Optional<User> getUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to get user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            // 예외 처리 로직 추가 (예: 로그 출력)
            e.printStackTrace();
            throw new RuntimeException("Failed to get all users", e);
        }
    }

    @Override
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

    @Override
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