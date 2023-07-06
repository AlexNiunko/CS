package com.epam.cs.dao.impl;


import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.BaseDao;
import com.epam.cs.dao.UserDao;
import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;
import com.epam.cs.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.epam.cs.command.AttributeName.*;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    public static final String SELECT_PASSWORD = "SELECT password,name from users WHERE e_mail = ?";
    public static final String FIND_PASSWORD = "SELECT password from users WHERE password=?";
    public static final String FIND_IDENTIFICATION_NUMBER = "SELECT identification_number from users WHERE identification_number =?";
    public static final String FIND_EMAIL = "SELECT e_mail from users WHERE e_mail=?";
    public static final String SELECT_USER_BY_EMAIL = "SELECT user_id,name,surname,date_of_expirity,identification_number,password,e_mail,users_role FROM users WHERE e_mail=? ";
    public static final String SELECT_USER_BY_ID = "SELECT user_id,name,surname,date_of_expirity,identification_number,password,e_mail,users_role FROM users WHERE user_id=? ";
    public static final String ACCESS_LEVEL_BY_EMAIL = "SELECT level_name FROM  access_level INNER JOIN users ON users.user_access_level=id_level WHERE e_mail=?";
    public static final String INSERT_USER = "INSERT INTO users (name,surname,date_of_expirity,identification_number,password,e_mail,users_role) VALUES (?, ?,?,?,?,?,?)";

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
    public List<User> findAll() {
        return null;
    }

    @Override
    public boolean update(User user) {

        return true;
    }

    @Override
    public boolean authenticate(String eMail, String password) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD)) {
            statement.setString(1, eMail);
            try (ResultSet resultSet = statement.executeQuery()) {
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

    @Override
    public Optional<User> findUserByEmail(String eMail) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, eMail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setName(resultSet.getString(NAME));
                    user.setSurName(resultSet.getString(AttributeName.SURNAME));
                    user.setMail(resultSet.getString(AttributeName.E_MAIL));
                    user.setDateOfExpirity(resultSet.getString(AttributeName.DATE_OF_EXPIRITY));
                    user.setDrivingLicenseNumber(resultSet.getString(AttributeName.IDENTIFICATION_NUMBER));
                    user.setId(Integer.parseInt(resultSet.getString(USERS_ID)));
                    optionalUser = Optional.of(user);
                } else {
                    optionalUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findUserById(String idUser) throws DaoException {
        Optional<User> optionalUser;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setString(1, idUser);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setName(resultSet.getString(NAME));
                    user.setSurName(resultSet.getString(AttributeName.SURNAME));
                    user.setMail(resultSet.getString(AttributeName.E_MAIL));
                    user.setDateOfExpirity(resultSet.getString(AttributeName.DATE_OF_EXPIRITY));
                    user.setDrivingLicenseNumber(resultSet.getString(AttributeName.IDENTIFICATION_NUMBER));
                    user.setId(Integer.parseInt(resultSet.getString(USERS_ID)));
                    optionalUser = Optional.of(user);
                    logger.warn("User by id {} is {}",idUser,user);
                } else {
                    optionalUser = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public Optional<String> findPassword(String password) throws DaoException {
        Optional<String> optionalPassword;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD)) {
            statement.setString(1, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalPassword = Optional.of(resultSet.getString(PASSWORD));
                    logger.warn("{}", optionalPassword.get());
                } else {
                    optionalPassword = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalPassword;
    }

    @Override
    public Optional<String> findEmail(String email) throws DaoException {
        Optional<String> optionalEmail;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalEmail = Optional.of(resultSet.getString(E_MAIL));
                    logger.warn("{}", optionalEmail.get());
                } else {
                    optionalEmail = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalEmail;
    }

    @Override
    public Optional<String> findIdentificationNumber(String identificationNumber) throws DaoException {
        Optional<String> optionalIdentificationNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_IDENTIFICATION_NUMBER)) {
            statement.setString(1, identificationNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalIdentificationNumber = Optional.of(resultSet.getString(IDENTIFICATION_NUMBER));
                } else {
                    optionalIdentificationNumber = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalIdentificationNumber;
    }

    @Override
    public Optional<String> getAccessLevelByEmail(String email) throws DaoException {
        Optional<String> optionalAccessLevel;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ACCESS_LEVEL_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    optionalAccessLevel = Optional.of(resultSet.getString(LEVEL_NAME));
                } else {
                    optionalAccessLevel = Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalAccessLevel;
    }

}



