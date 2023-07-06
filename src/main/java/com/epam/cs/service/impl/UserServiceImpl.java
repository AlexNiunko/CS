package com.epam.cs.service.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.impl.UserDaoImpl;
import com.epam.cs.entity.AccessLevel;
import com.epam.cs.util.PasswordEncryption;
import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.UserService;
import com.epam.cs.validator.ParametrValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;


public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static UserServiceImpl instance = new UserServiceImpl();
    private static final String FREE="free";
    private static final String BLOCKED="blocked";

    private UserServiceImpl() {

    }

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addUser(Map<String, String> inputData, HttpServletRequest request) throws ServiceException {
        logger.warn("In usr service addUser");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        ParametrValidator parametrValidator = ParametrValidator.getInstance();
        try {
            if (parametrValidator.validateAndCorrectInputData(inputData,request)) {
                User user = new User();
                user.setName(inputData.get(AttributeName.NAME));
                user.setSurName(inputData.get(AttributeName.SURNAME));
                user.setDateOfExpirity(inputData.get(AttributeName.DATE_OF_EXPIRITY));
                user.setDrivingLicenseNumber(inputData.get(AttributeName.IDENTIFICATION_NUMBER));
                user.setMail(inputData.get(AttributeName.E_MAIL));
                String passwordEncrypted = PasswordEncryption.encrypt(inputData.get(AttributeName.PASSWORD));
                user.setPass(passwordEncrypted);
                boolean match = userDao.insert(user);
                return match;
            } else {
                logger.error("invalid user data ");
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean authenticate(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        ParametrValidator parametrValidator = ParametrValidator.getInstance();
        boolean match = false;
        try {
            if (parametrValidator.validateEmail(email) &&
                    parametrValidator.validatePassword(password)) {
                String encryptPassword = PasswordEncryption.encrypt(password);
                match = userDao.authenticate(email, encryptPassword);
                logger.error("Email and password are valid");
            } else {
                logger.error("invalid email or password");
                return match;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

//    @Override
//    public boolean ifExistUserByDriverLicenseOrEmail(Map<String, String> inputData) throws ServiceException {
//        UserDaoImpl userDao = UserDaoImpl.getInstance();
//        Optional<User>optionalUserByEmail;
//        Optional<User>optionalUserByIdentificationNumber;
//        boolean match=false ;
//        try {
//            optionalUserByEmail=userDao.findUserByEmail(inputData.get(AttributeName.E_MAIL));
//            optionalUserByIdentificationNumber=userDao.findUserByIdentificationNumber(AttributeName.IDENTIFICATION_NUMBER);
//
//            if ((optionalUserByEmail.isPresent() && optionalUserByIdentificationNumber.isPresent())){
//                logger.warn("Exists by Email and ID driver {}",optionalUserByEmail.get());
//                match=optionalUserByEmail.get().getMail().equals(inputData.get(AttributeName.E_MAIL))||optionalUserByIdentificationNumber.get().equals(AttributeName.IDENTIFICATION_NUMBER)
//                || optionalUserByEmail.get().getDrivingLicenseNumber().equals(inputData.get(AttributeName.IDENTIFICATION_NUMBER)) || optionalUserByIdentificationNumber.get().getMail().equals(AttributeName.E_MAIL);
//            }else if (optionalUserByEmail.isPresent()){
//                logger.warn("Exists by only Email");
//                match=optionalUserByEmail.get().getMail().equals(inputData.get(AttributeName.E_MAIL)) || optionalUserByEmail.get().getDrivingLicenseNumber().equals(inputData.get(AttributeName.IDENTIFICATION_NUMBER)) ;
//            }else if (optionalUserByIdentificationNumber.isPresent()){
//                logger.warn("Exists by only ID driver");
//                match=optionalUserByIdentificationNumber.get().getMail().equals(inputData.get(AttributeName.E_MAIL)) || optionalUserByIdentificationNumber.get().getDrivingLicenseNumber().equals(inputData.get(AttributeName.IDENTIFICATION_NUMBER));
//            } else logger.warn("Is not exist");
//            logger.warn("Service {} ", match);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
//        return match;
//    }
    @Override
    public Optional<User> findUserByEmail(Map<String, String> inputData) throws ServiceException{
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Optional<User>optionalUser;
        try {
            optionalUser=userDao.findUserByEmail(inputData.get(AttributeName.E_MAIL));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean findPassword(String password) throws ServiceException {
        String encryptPassword=PasswordEncryption.encrypt(password);
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        Optional<String>optionalPassword;
        try {
            optionalPassword=userDao.findPassword(encryptPassword);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        logger.warn("{}",optionalPassword.isPresent());
        return optionalPassword.isPresent();
    }

    @Override
    public boolean findEmail(String email) throws ServiceException {
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        Optional<String>optionalEmail;
        try {
            optionalEmail=userDao.findEmail(email);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return optionalEmail.isPresent();
    }

    @Override
    public boolean findIdentificationNumber(String identificationNumber) throws ServiceException {
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        Optional<String>optionalidentificationNumber;
        try {
            optionalidentificationNumber=userDao.findIdentificationNumber(identificationNumber);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return optionalidentificationNumber.isPresent();
    }

    @Override
    public AccessLevel getAccessLevelByEmail(String email) throws ServiceException {
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        Optional<String>accessLevel;
        AccessLevel level;
        try {
            accessLevel=userDao.getAccessLevelByEmail(email);
        }catch (DaoException e){
            logger.error("Failed to get access level");
            throw new ServiceException(e);
        }
        if (accessLevel.isPresent()){
            String accessLev= accessLevel.get();
            switch (accessLev.toLowerCase()){
                case FREE -> level=AccessLevel.FREE;
                case BLOCKED -> level=AccessLevel.BLOCKED;
                default -> throw new ServiceException("Unexpected value: " + accessLev.toLowerCase());
            }
        } else {
            logger.error("Missing access level");
            throw new ServiceException();
        }
        return level;
    }
}


