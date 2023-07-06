package com.epam.cs.command.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.entity.Car;
import com.epam.cs.entity.Order;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeTheCarCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        CarServiceImpl carService=CarServiceImpl.getInstance();
        HttpSession session= request.getSession();
        Car car= (Car) session.getAttribute(AttributeName.ORDERS_CAR);
        Order order= (Order) session.getAttribute(AttributeName.ORDER);
        session.removeAttribute(AttributeName.ORDERS_CAR);
        session.removeAttribute(AttributeName.ORDER);
        car.setCurrentStatus(Boolean.TRUE);
        try {
            if (carService.updateCarCurrentStatus(car,order)) {
                session.setAttribute(AttributeName.ORDER,order);
                router.setRedirect();
                router.setPage(PagePath.CHOOSE_A_CAR_PAGE);
            } else {
                logger.warn("Failed to change users car");
                router.setPage(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            logger.error("Error while update car current status");
            throw new CommandException(e);
        }
        return router;
    }
}
