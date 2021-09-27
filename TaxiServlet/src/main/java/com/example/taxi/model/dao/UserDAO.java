package com.example.taxi.model.dao;

import com.example.taxi.model.entity.Car;
import com.example.taxi.model.entity.User;
import com.example.taxi.tools.PBKDF2Hasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User checkLogin(String email, String password) throws SQLException,
            ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        ResultSet result = statement.executeQuery();

        User user = null;

        if (result.next()) {
            String token = result.getString("password");
            try {
                if ((new PBKDF2Hasher()).checkPassword(password.toCharArray(), token)) {
                    user = new User();
                    user.setId(result.getLong("id"));
                    user.setLastName(result.getString("last_name"));
                    user.setFirstName(result.getString("first_name"));
                    user.setLogin(result.getString("user_name"));
                    user.setEmail(email);
                    if ("ROLE_ADMIN".equals(result.getString("roles"))) {
                        user.setRole(User.ROLE.ADMIN);
                    } else {
                        user.setRole(User.ROLE.USER);
                    }
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        connection.close();

        return user;
    }

    public User newUser(String firstName, String lastName, String userName, String email, String password) throws SQLException, ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "INSERT INTO users ( email, first_name, last_name, password, roles, user_name, enabled ) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, firstName);
        statement.setString(3, lastName);
        statement.setString(4, (new PBKDF2Hasher()).hash(password.toCharArray()));
        statement.setString(5, "ROLE_USER");
        statement.setString(6, userName);
        statement.setInt(7, 1);

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        User user;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user = new User();
                user.setId(generatedKeys.getLong(1));
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setLogin(userName);
                user.setRole(User.ROLE.USER);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }

        connection.close();
        return user;
    }

    public List<User> getUsers() throws SQLException,
            ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM users WHERE roles = 'ROLE_USER'";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();
        List<User> list = new ArrayList<>();
        while (result.next()) {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setLastName(result.getString("last_name"));
            user.setFirstName(result.getString("first_name"));
            user.setLogin(result.getString("user_name"));
            list.add(user);
        }
        connection.close();
        return list;
    }

    public List<User> get() throws SQLException,
            ClassNotFoundException {

        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet result = statement.executeQuery();
        List<User> list = new ArrayList<>();
        while (result.next()) {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setLastName(result.getString("last_name"));
            user.setFirstName(result.getString("first_name"));
            user.setLogin(result.getString("user_name"));
            user.setEmail(result.getString("email"));
            user.setRole(User.ROLE.parseRole(result.getString("roles")));
            list.add(user);
        }
        connection.close();
        return list;
    }

    public User get(int id) throws SQLException,
            ClassNotFoundException {
        Connection connection = ConnectionPool.getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet result = statement.executeQuery();
        User user = null;
        if (result.next()) {
            user = new User();
            user.setId(result.getLong("id"));
            user.setLastName(result.getString("last_name"));
            user.setFirstName(result.getString("first_name"));
            user.setLogin(result.getString("user_name"));
            user.setEmail(result.getString("email"));
            user.setRole(User.ROLE.parseRole(result.getString("roles")));
        }
        connection.close();
        return user;
    }

    public void save(User user) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getConnection();
        String sql;
        if (user.getId() == -1)
            sql = "INSERT INTO users (email, enabled, first_name, last_name, password, roles, user_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        else
            sql = "UPDATE users SET email = ?, enabled = ?, first_name = ?, last_name = ?, password = ?, roles = ?, user_name = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setInt(2, 1);
        statement.setString(3, user.getFirstName());
        statement.setString(4, user.getLastName());
        statement.setString(5, user.getPassword());
        statement.setString(6, user.getRole().toString());
        statement.setString(7, user.getLogin());
        if (user.getId() != -1)
            statement.setLong(8, user.getId());

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        connection.close();
    }

    public void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getConnection();
        String sql = "DELETE FROM users WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        int result = statement.executeUpdate();
        if (result == 0) {
            throw new SQLException("Deleting user failed, no rows affected.");
        }
        connection.close();
    }
}
