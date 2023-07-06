package com.epam.cs.command.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.entity.Car;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

public class ChooseTheCarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        Car car=new Car();
        CarServiceImpl carService=CarServiceImpl.getInstance();
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute(AttributeName.USER);
        String idCar=request.getParameter(AttributeName.ID_CAR);
        car.setId(Integer.parseInt(idCar));
        car.setCurrentStatus(Boolean.FALSE);
        try {
            if (carService.getCarById(idCar,car) && carService.updateCarCurrentStatus(car)){
                session.setAttribute(AttributeName.ORDERS_CAR,car);
                if (orderService.findPreOrderByEmail(user)){
                    Order order= (Order) session.getAttribute(AttributeName.ORDER);
                    goToPage(car,order,request,router);
                } else {
                    logger.warn( "car {}",car);
                    router.setRedirect();
                    router.setPage(PagePath.ORDERS_PAGE_PAYMENT_METHOD);
                }
            } else {
                logger.warn("Failed to select the car");
                router.setPage(PagePath.CHOOSE_A_CAR_PAGE);
            }
        } catch (ServiceException e) {
            logger.warn("Failed to choose the car");
            throw new CommandException(e);
        }
        return router;
    }
    private void  goToPage(Car car,Order order,HttpServletRequest request,Router router) throws ServiceException {
        HttpSession session= request.getSession();
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        if (orderService.insertCarToPreOrder(car,order)){
            session.removeAttribute(AttributeName.ORDER);
            session.setAttribute(AttributeName.ORDER,order);
            router.setRedirect();
            router.setPage(PagePath.ORDER_PAGE_READY_TO_START);
        } else {
            router.setPage(PagePath.CHOOSE_A_CAR_PAGE);
        }
    }
}
