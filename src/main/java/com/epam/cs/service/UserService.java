package com.epam.cs.service;

import com.epam.cs.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

public interface UserService {

    boolean addUser(Map<String, String> inputData) throws ServiceException;

    boolean authenticate(String email, String password) throws ServiceException;

}
