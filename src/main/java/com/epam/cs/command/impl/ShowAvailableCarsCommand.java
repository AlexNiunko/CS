package com.epam.cs.command.impl;

import com.epam.cs.command.*;
import com.epam.cs.entity.Car;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAvailableCarsCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.warn("In ShowAvailableCarsCommand command");
        Router router=new Router();
        HttpSession session= request.getSession();
        CarServiceImpl carService=CarServiceImpl.getInstance();
        List<Car>availableCars;
        try{
            availableCars=carService.showAvailableCar();
            session.setAttribute(AttributeName.CARS,availableCars);
            session.removeAttribute(PagesMessage.LOGIN_MSG);
            router.setRedirect();
            router.setPage(PagePath.MAINPAGE);
        }
        catch (ServiceException e){
            logger.error("ShowAvailableCarCommand command error. {} ",e.getMessage());
            throw new CommandException("ShowAvailableCarCommand command error.",e);
        }
        return router;
    }
}
