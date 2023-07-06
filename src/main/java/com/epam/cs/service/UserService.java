package com.epam.cs.service;

import com.epam.cs.entity.AccessLevel;
import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    boolean addUser(Map<String, String> inputData,HttpServletRequest request) throws ServiceException;

    boolean authenticate(String email, String password) throws ServiceException;
//    boolean ifExistUserByDriverLicenseOrEmail(Map<String, String> inputData) throws ServiceException;
    Optional<User> findUserByEmail(Map<String, String> inputData) throws ServiceException;

    boolean findPassword(String password ) throws ServiceException;
    boolean findEmail(String email ) throws ServiceException;
    boolean findIdentificationNumber(String identificationNumber ) throws ServiceException;
    AccessLevel getAccessLevelByEmail(String email) throws ServiceException, DaoException;



}
