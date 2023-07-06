package com.epam.cs.command.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.entity.Car;
import com.epam.cs.entity.CreditCard;
import com.epam.cs.entity.Order;
import com.epam.cs.entity.User;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.CarServiceImpl;
import com.epam.cs.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddCreditCardCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final String CARD_METHOD="card";
    @Override
    public Router execute(HttpServletRequest request) throws CommandException{
        Router router=new Router();
        Order order=new Order();
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        HttpSession session= request.getSession();
        User user=(User)session.getAttribute(AttributeName.USER);
        Car car=(Car)session.getAttribute(AttributeName.ORDERS_CAR);
        String idUser= String.valueOf(user.getId());
        String idCar= String.valueOf(car.getId());
        logger.warn(" id_user is {} , id_car is {}",idUser,idCar);
        HashMap<String,String> preOrderCreditCardInputData=new HashMap<>();
        preOrderCreditCardInputData.put(AttributeName.CARD_NUMBER,request.getParameter(AttributeName.CARD_NUMBER));
        preOrderCreditCardInputData.put(AttributeName.CARD_EXPIRY_DATE,request.getParameter(AttributeName.CARD_EXPIRY_DATE));
        preOrderCreditCardInputData.put(AttributeName.CARD_OWNER_NAME,request.getParameter(AttributeName.CARD_OWNER_NAME));
        preOrderCreditCardInputData.put(AttributeName.CARD_CVC,request.getParameter(AttributeName.CARD_CVC));
        preOrderCreditCardInputData.put(AttributeName.ID_CAR,idCar);
        preOrderCreditCardInputData.put(AttributeName.USERS_ID,idUser);
        try{
            if (orderService.addCreditCard( preOrderCreditCardInputData,request)){
                order.setMethod(CARD_METHOD);
                CreditCard creditCard=new CreditCard();
                if (addPreOrder(preOrderCreditCardInputData,creditCard,order)){
                    session.setAttribute(AttributeName.ORDER,order);
                    router.setRedirect();
                    router.setPage(PagePath.ORDER_PAGE_READY_TO_START);
                } else {
                    logger.error("Can not to add pre_order in DB");
                    router.setPage(PagePath.ERROR_500);
                }
            } else {
                for (Map.Entry<String,String> map: preOrderCreditCardInputData.entrySet()) {
                    request.setAttribute(map.getKey(), map.getValue());
                }
                logger.warn("Incorrect inputdata");
                router.setPage(PagePath.ORDERS_PAGE_ADD_CREDIT_CARD);
            }
        }catch (ServiceException e){
            logger.error("Error in Add credit Card");
            throw new CommandException();
        }
        return router;
    }
    private boolean addPreOrder(HashMap<String,String> preOrderData,CreditCard card,Order order) throws CommandException{
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        try {
            if (orderService.fExistCreditCardInDb(preOrderData,card)){
                logger.warn(card.getId());
                return orderService.addPreOrderWithCreditCard(preOrderData,card,order);
            }else {
                return false;
            }
        } catch (ServiceException e) {
            logger.error("Failed to check credit card");
            throw new CommandException(e);
        }
    }
}
