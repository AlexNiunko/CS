package com.epam.cs.dao;

import com.epam.cs.exception.DaoException;

public interface UserDao {
    boolean authenticate(String login,String password) throws DaoException;
}
