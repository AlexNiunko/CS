package com.epam.cs.dao.impl;


import com.epam.cs.dao.BaseDao;
import com.epam.cs.dao.UserDao;
import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;
import com.epam.cs.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    public static final String SELECT_PASSWORD = "SELECT pass from users WHERE mail = ?";

    public static final String INSERT_USER = "INSERT INTO users (name,surname,date_of_expirity,identification_number,pass,mail,users_role) VALUES (?, ?,?,?,?,?,?)";

    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurName());
            statement.setString(3, user.getDateOfExpirity());
            statement.setString(4, user.getDrivingLicenseNumber());
            statement.setString(5, user.getPass());
            statement.setString(6, user.getMail());
            statement.setString(7, String.valueOf(user.getRole()));
            statement.executeUpdate();
            match = true;
        } catch (SQLException e) {
            logger.error("Failed to insert into users {}", e.getMessage());
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll( ) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD)) {
            statement.setString(1, login);
            try(ResultSet resultSet = statement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(1);
                    match = password.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
