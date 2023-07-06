package com.epam.cs.dao;

import com.epam.cs.entity.User;
import com.epam.cs.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean authenticate(String login,String password) throws DaoException;
    Optional<User> findUserByEmail(String email) throws DaoException;
    Optional<User> findUserById(String idUser) throws DaoException;
    Optional<String> findPassword(String password) throws DaoException;
    Optional<String> findEmail(String email) throws DaoException;
    Optional<String> findIdentificationNumber(String identificationNumber) throws DaoException;
    Optional<String> getAccessLevelByEmail(String email) throws DaoException;


}
