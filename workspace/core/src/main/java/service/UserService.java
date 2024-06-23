package service;

import dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> getUser(int userSeq);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int userSeq);
}