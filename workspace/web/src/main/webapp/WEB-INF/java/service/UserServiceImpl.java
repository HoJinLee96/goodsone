package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDAO;
import dao.UserDAOImpl;
import dto.User;

@Service
@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAOImpl userDAO) {
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
    public Optional<User> getUser(String email) {
        try {
            return userDAO.getUser(email);
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