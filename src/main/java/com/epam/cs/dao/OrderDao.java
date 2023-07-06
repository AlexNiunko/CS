package com.epam.cs.dao;

import com.epam.cs.entity.Car;
import com.epam.cs.entity.CreditCard;
import com.epam.cs.entity.Order;
import com.epam.cs.exception.DaoException;

import java.util.HashMap;
import java.util.Optional;

public interface OrderDao {
    boolean findIdOrderByParam(Order order) throws DaoException;

    Optional<Order> findOrderByEmail(String email) throws DaoException;
    boolean addPreOrderWithCreditCard(Order order) throws DaoException;

    boolean addPreOrderWithCash(Order order) throws DaoException;

    boolean addCreditCard(CreditCard card) throws DaoException;

    Optional<CreditCard> getIdCardByNumberAndName(HashMap<String, String> cardData) throws DaoException;

    Optional<CreditCard> findCreditCardById(String idCreditCard)throws DaoException;

    boolean addIdCreditCardToOrder(CreditCard card, Order order) throws DaoException;

    boolean deletePaymentMethodFromOrder(Order order) throws DaoException;

    boolean deleteCreditCardFromOrder(Order order) throws DaoException;

    boolean insertCarIntoPreorder(Car car,Order order) throws DaoException;
    boolean makeOrderFromPreorder(Order order) throws DaoException;
}
