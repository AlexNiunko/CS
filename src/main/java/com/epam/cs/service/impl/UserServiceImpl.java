package com.epam.cs.service.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.impl.UserDaoImpl;
import com.epam.cs.util.PasswordEncryption;
import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.UserService;
import com.epam.cs.validator.ParametrValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addUser(Map<String, String> inputData) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        ParametrValidator parametrValidator=ParametrValidator.getInstance();
        try {
            if (parametrValidator.validateAndCorrectInputData(inputData)) {
                User user = new User();
                user.setName(inputData.get(AttributeName.NAME));
                user.setSurName(inputData.get(AttributeName.SURNAME));
                user.setDateOfExpirity(inputData.get(AttributeName.DATE_OF_EXPIRITY));
                user.setDrivingLicenseNumber(inputData.get(AttributeName.IDENTIFICATION_NUMBER));
                user.setMail(inputData.get(AttributeName.E_MAIL));
                String passwordEncrypted= PasswordEncryption.encrypt(inputData.get(AttributeName.PASSWORD));
                user.setPass(passwordEncrypted);
                boolean match = userDao.insert(user);
                return match;
            } else {
                logger.error("invalid user data in serice");
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean authenticate(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        ParametrValidator parametrValidator=ParametrValidator.getInstance();
        boolean match = false;
        try {
            if (parametrValidator.validateEmail(email)&&
                    parametrValidator.validatePassword(password)) {
                String encryptPassword= PasswordEncryption.encrypt(password);
                match = userDao.authenticate(email,encryptPassword);
            } else {
                logger.error("invalid email or password");
                return match;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

}
