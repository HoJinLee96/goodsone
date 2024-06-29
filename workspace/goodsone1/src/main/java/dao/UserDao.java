package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dto.User;

@Repository
public class UserDao  {
    private final DataSource dataSource;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Create
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO user (user_emil, user_password, user_oldpassword, user_name, user_nickname, user_birth, user_phone_agency, user_phone_number, user_address, created_at, user_status, user_signtype) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserEmail());
            statement.setString(2, user.getUserPassword());
            statement.setString(3, user.getUserOldPassword());
            statement.setString(4, user.getUserName());
            statement.setString(5, user.getUserNickname());
            statement.setString(6, user.getUserBirth());
            statement.setString(7, user.getUserPhoneAgency());
            statement.setString(8, user.getUserPhoneNumber());
            statement.setString(9, user.getUserAddress());
            statement.setTimestamp(10, Timestamp.valueOf(user.getCreatedAt()));
            statement.setString(11, user.getUserStatus());
            statement.setString(12, user.getUserSignType());

            statement.executeUpdate();
        }
    }

    // Read (Get User by ID)
    public Optional<User> getUser(int userSeq) throws SQLException {
        String sql = "SELECT * FROM user WHERE user_seq = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userSeq);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User.Builder()
                        .userSeq(resultSet.getInt("user_seq"))
                        .userEmail(resultSet.getString("user_email"))
                        .userPassword(resultSet.getString("user_password"))
                        .userOldPassword(resultSet.getString("user_oldpassword"))
                        .userName(resultSet.getString("user_name"))
                        .userNickname(resultSet.getString("user_nickname"))
                        .userBirth(resultSet.getString("user_birth"))
                        .userPhoneAgency(resultSet.getString("user_phone_agency"))
                        .userPhoneNumber(resultSet.getString("user_phone_number"))
                        .userAddress(resultSet.getString("user_address"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(resultSet.getTimestamp("updated_at") != null ? resultSet.getTimestamp("updated_at").toLocalDateTime() : null)
                        .userStatus(resultSet.getString("user_status"))
                        .userSignType(resultSet.getString("user_signtype"))
                        .build();

                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }
    
    // Read (Get User by ID)
    public Optional<User> getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE user_email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User.Builder()
                        .userSeq(resultSet.getInt("user_seq"))
                        .userEmail(resultSet.getString("user_email"))
                        .userPassword(resultSet.getString("user_password"))
                        .userOldPassword(resultSet.getString("user_oldpassword"))
                        .userName(resultSet.getString("user_name"))
                        .userNickname(resultSet.getString("user_nickname"))
                        .userBirth(resultSet.getString("user_birth"))
                        .userPhoneAgency(resultSet.getString("user_phone_agency"))
                        .userPhoneNumber(resultSet.getString("user_phone_number"))
                        .userAddress(resultSet.getString("user_address"))
                        .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(resultSet.getTimestamp("updated_at") != null ? resultSet.getTimestamp("updated_at").toLocalDateTime() : null)
                        .userStatus(resultSet.getString("user_status"))
                        .userSignType(resultSet.getString("user_signtype"))
                        .build();

                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    // Read (Get all Users)
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User.Builder()
                    .userSeq(resultSet.getInt("user_seq"))
                    .userEmail(resultSet.getString("user_email"))
                    .userPassword(resultSet.getString("user_password"))
                    .userOldPassword(resultSet.getString("user_oldpassword"))
                    .userName(resultSet.getString("user_name"))
                    .userNickname(resultSet.getString("user_nickname"))
                    .userBirth(resultSet.getString("user_birth"))
                    .userPhoneAgency(resultSet.getString("user_phone_agency"))
                    .userPhoneNumber(resultSet.getString("user_phone_number"))
                    .userAddress(resultSet.getString("user_address"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(resultSet.getTimestamp("updated_at") != null ? resultSet.getTimestamp("updated_at").toLocalDateTime() : null)
                    .userStatus(resultSet.getString("user_status"))
                    .userSignType(resultSet.getString("user_signtype"))
                    .build();
                users.add(user);
            }
        }
        return users;
    }

    // Update
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user SET user_email = ?, user_password = ?, user_oldpassword = ?, user_name = ?, user_nickname = ?, user_birth = ?, user_phone_agency = ?, user_phone_number = ?, user_address = ?, updated_at = ?, user_status = ?, user_signtype = ? WHERE user_seq = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getUserEmail());
            statement.setString(2, user.getUserPassword());
            statement.setString(3, user.getUserOldPassword());
            statement.setString(4, user.getUserName());
            statement.setString(5, user.getUserNickname());
            statement.setString(6, user.getUserBirth());
            statement.setString(7, user.getUserPhoneAgency());
            statement.setString(8, user.getUserPhoneNumber());
            statement.setString(9, user.getUserAddress());
            statement.setTimestamp(10, user.getUpdatedAt() != null ? Timestamp.valueOf(user.getUpdatedAt()) : null);
            statement.setString(11, user.getUserStatus());
            statement.setString(12, user.getUserSignType());
            statement.setInt(13, user.getUserSeq());

            statement.executeUpdate();
        }
    }

    // Delete
    public void deleteUser(int userSeq) throws SQLException {
        String sql = "DELETE FROM user WHERE user_seq = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userSeq);
            statement.executeUpdate();
        }
    }
}