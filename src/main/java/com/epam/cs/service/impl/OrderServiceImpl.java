package com.epam.cs.service.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.dao.impl.CarDaoImpl;
import com.epam.cs.dao.impl.OrderDaoImpl;
import com.epam.cs.dao.impl.UserDaoImpl;
import com.epam.cs.entity.*;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.OrderService;
import com.epam.cs.validator.ParametrValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class OrderServiceImpl  implements OrderService {

    private static final Logger logger = LogManager.getLogger();
    private static final String PRE_ORDER_STATUS="pre_order";
    private static OrderServiceImpl instance = new OrderServiceImpl();
    public OrderServiceImpl() {
    }
    public static OrderServiceImpl getInstance() {
        return instance;
    }


    @Override
    public Optional<OrderStatus> findPreOrderByEmail(String email) throws ServiceException {
        logger.warn("into order service");
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        Optional<OrderStatus>optionalStatus;
        Optional<Order>optionalOrder;
        try {
            optionalOrder=orderDao.findOrderByEmail(email);
            if (optionalOrder.isPresent()){
                optionalStatus=Optional.of(optionalOrder.get().getOrderStatus());
                logger.warn("Order is exist");
            } else {
                optionalStatus=Optional.empty();
                logger.warn("Order is not exist");
            }
        } catch (DaoException e) {
            logger.warn("Can not to find order");
            throw new ServiceException(e);
        }

        return optionalStatus;
    }

    @Override
    public boolean addPreOrderWithCash(String idCar, String idUser, Order order) throws ServiceException {
        boolean result=false;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        Optional<Car>optionalCar;
        Optional<User>optionalUser;
        try {
            optionalUser=userDao.findUserById(idUser);
            optionalCar=carDao.findCarByID(idCar);
            if (optionalCar.isPresent()&& optionalUser.isPresent()){
                order.setIdCar(Integer.parseInt(idCar));
                order.setIdUser(Integer.parseInt(idUser));
                order.setOrderStatus(OrderStatus.PRE_ORDER.toString());
                logger.warn("Application time is  {}",order.getApplicationTime());
                result=orderDao.addPreOrderWithCash(order);
                return result;
            } else {
                logger.error("invalid order data");
                return result;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addCreditCard(HashMap<String, String> cardData, HttpServletRequest request) throws ServiceException {
        ParametrValidator validator=ParametrValidator.getInstance();
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        try{
            if (validator.validateAndCorrectInputData(cardData,request)){
                CreditCard card=new CreditCard();
                card.setNumber(cardData.get(AttributeName.CARD_NUMBER));
                card.setCardHolderName(cardData.get(AttributeName.CARD_OWNER_NAME));
                card.setDateExpiry(cardData.get(AttributeName.CARD_EXPIRY_DATE));
                card.setCvc(cardData.get(AttributeName.CARD_CVC));
                card.setCurrentSum();
                logger.warn("card {}",card);
                return orderDao.addCreditCard(card);
            } else {
                logger.error("Invalid card data");
                return false;
            }
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }
    @Override
    public boolean addPreOrderWithCreditCard(HashMap<String, String> preOrderData, CreditCard card, Order order) throws ServiceException{
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        UserDaoImpl userDao=UserDaoImpl.getInstance();
        CarDaoImpl carDao=CarDaoImpl.getInstance();
        Optional<Car>optionalCar;
        Optional<User>optionalUser;
        Optional<CreditCard>optionalCreditCard;
        try {
            optionalUser=userDao.findUserById(preOrderData.get(AttributeName.USERS_ID));
            optionalCar=carDao.findCarByID(preOrderData.get(AttributeName.ID_CAR));
            logger.warn("id creditCard is {}",card.getId());
            optionalCreditCard=orderDao.findCreditCardById(String.valueOf(card.getId()));
            if (optionalCar.isPresent() && optionalUser.isPresent() && optionalCreditCard.isPresent()){
                order.setIdCar(Integer.parseInt(preOrderData.get(AttributeName.ID_CAR)));
                order.setIdUser(Integer.parseInt(preOrderData.get(AttributeName.USERS_ID)));
                order.setOrderStatus(OrderStatus.PRE_ORDER.toString());
                order.setCard(optionalCreditCard.get());
                logger.warn("Order is {}",order);
                if (orderDao.addPreOrderWithCreditCard(order)){
                    return true;
                } else {
                    return false;
                }
            } else {
                logger.error("invalid order data when added credit card");
                return false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
    @Override
    public boolean fExistCreditCardInDb(HashMap<String, String> cardData, CreditCard card) throws ServiceException{
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        Optional<CreditCard>optionalCreditCard;
        try{
            optionalCreditCard=orderDao.getIdCardByNumberAndName(cardData);
            if (optionalCreditCard.isPresent()){
                card.setId(optionalCreditCard.get().getId());
                logger.warn("Id credit card is {}",card.getId());
                return true;
            } else {
                return false;
            }

        } catch (DaoException e){
            logger.error("Can't find ID credit card in DB");
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deletePaymentMethod(Order order) throws ServiceException {
        boolean result;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        logger.warn("INTO order service, order is {}",order);
        try{
            if (orderDao.findIdOrderByParam(order)){
                result=orderDao.deletePreorder(order);
            } else {
                logger.error("Can not find Id order in DB");
                result=false;
            }
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }
    @Override
    public boolean addPaymentMethodIntoOrder(Order order) throws ServiceException{
        boolean result;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        try{
            if (orderDao.findIdOrderByParam(order)){
                result=orderDao.insertIntoOrderPaymentMethod(order);
            }else {
                logger.warn("Can not find to id order in DB");
                result=false;
            }
        } catch (DaoException e){
            logger.error("Can not to add Payment method to order");
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean findPreOrderByEmail(User user) throws ServiceException {
        boolean result;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        Optional<Order>optionalOrder;
        try{
            optionalOrder=orderDao.findOrderByEmail(user.getMail());
            if (optionalOrder.isPresent()){
                result=optionalOrder.get().getOrderStatus().equals(OrderStatus.PRE_ORDER);
            }else {
                logger.warn("Can not find order with current user");
                result=false;
            }
        }catch (DaoException e){
            logger.error("Failed to find order by id user");
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean insertCarToPreOrder(Car car,Order order) throws ServiceException {
        boolean result;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        try{
            result=orderDao.insertCarIntoPreorder(car,order);
            if (result){
                order.setIdCar(car.getId());
                logger.warn("Car has inserted to pre-order");
            } else {
                logger.warn("Car hasn't insert to pre-order");
            }
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean startMoving(Order order) throws ServiceException {
        boolean result;
        OrderDaoImpl orderDao=OrderDaoImpl.getInstance();
        try{
            if (orderDao.findIdOrderByParam(order)){
                result=orderDao.makeOrderFromPreorder(order);
                logger.warn("Result making order is  - {}",result);
            } else {
                logger.warn("Failed to find Id order ");
                result=false;
            }
        }catch (DaoException e){
            throw new ServiceException();
        }
        return result;
    }
}
