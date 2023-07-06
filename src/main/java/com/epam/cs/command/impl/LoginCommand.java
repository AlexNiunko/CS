package com.epam.cs.command.impl;


import com.epam.cs.command.*;
import com.epam.cs.entity.AccessLevel;
import com.epam.cs.entity.OrderStatus;
import com.epam.cs.entity.User;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.UserService;
import com.epam.cs.service.impl.OrderServiceImpl;
import com.epam.cs.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.warn("In login Command");
        Router router=new Router();
        Map<String,String>userData=new HashMap<>();
        HttpSession session=request.getSession();
        String email=request.getParameter(AttributeName.E_MAIL);
        String password=request.getParameter(AttributeName.PASSWORD);
        userData.put(AttributeName.E_MAIL,email);
        UserService userService= UserServiceImpl.getInstance();
        String page;
        Optional<User>optionalUser;
        User user;
        try {
            if( (userService.authenticate(email,password)) ){
                AccessLevel level;
                try {
                    level=userService.getAccessLevelByEmail(email);
                    optionalUser=userService.findUserByEmail(userData);
                } catch (DaoException e) {
                    throw new CommandException(e);
                }
                if (level.equals(AccessLevel.BLOCKED)){
                    router.setRedirect();
                    page=PagePath.BLOCKED_PAGE;
                    router.setPage(page);
                    logger.warn("User is blocked");
                } else{
                    logger.warn("User is free");
                    user=optionalUser.get();
                    session.setAttribute(AttributeName.USER,user);
                    router.setRedirect();
                    page=goToPage(email);
                    router.setPage(page);
                }
            } else {
                logger.warn("There is not user");
                session.setAttribute(PagesMessage.WELCOME,"Incorrect login or password");
                router.setRedirect();
                page = PagePath.LOGIN;
                router.setPage(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }

   private String goToPage(String mail ) throws CommandException {
        logger.warn("in go to page method");
        String page;
        Optional<OrderStatus> optionalOrderStatus;
        logger.warn("in go to page method2");
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
       logger.warn("in go to page method3");
       try {
           logger.warn("in go to page method4");
           optionalOrderStatus=orderService.findPreOrderByEmail(mail);
           logger.warn("try order service");
           if (optionalOrderStatus.isPresent()){
               OrderStatus status=optionalOrderStatus.get();
               logger.warn("order is exist");
               switch (status){
                   case PRE_ORDER -> page=PagePath.ORDERS_PAGE_PAYMENT_METHOD;
                   case BEING_EXECUTED -> page=PagePath.ORDER_PAGE_MOVING;
                   default -> page=PagePath.USER_PAGE;
               }
           } else {
               page=PagePath.USER_PAGE;
           }
       } catch (ServiceException e) {
           logger.warn("Failed to redirect to user page");
           throw new CommandException(e);
       }
       return page;
   }



}
