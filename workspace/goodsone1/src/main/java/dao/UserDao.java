package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dto.User;

public interface UserDao {
    void addUser(User user) throws SQLException;
    Optional<User> getUser(int userSeq) throws SQLException;
    Optional<User> getUserByEmail(String email) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int userSeq) throws SQLException;
}