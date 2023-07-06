package com.epam.cs.service;

import com.epam.cs.entity.*;
import com.epam.cs.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Optional;

public interface OrderService {
    Optional<OrderStatus> findPreOrderByEmail(String email) throws ServiceException;
    boolean addPreOrderWithCash(String IdCar, String IdUser, Order order) throws ServiceException;

    boolean addCreditCard(HashMap<String ,String> orderData, HttpServletRequest request) throws ServiceException;

    boolean addPreOrderWithCreditCard(HashMap<String, String> preOrderData, CreditCard card, Order order) throws ServiceException;

    boolean fExistCreditCardInDb(HashMap<String, String> cardData, CreditCard card) throws ServiceException;

    boolean deletePaymentMethod(Order order) throws ServiceException;


    boolean addPaymentMethodIntoOrder(Order order) throws ServiceException;

    boolean findPreOrderByEmail(User user) throws ServiceException;
    boolean insertCarToPreOrder(Car car,Order order) throws ServiceException;
    boolean startMoving(Order order) throws ServiceException;
}
