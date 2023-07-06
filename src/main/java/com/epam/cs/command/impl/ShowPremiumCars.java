package com.epam.cs.command.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.entity.Car;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowPremiumCars implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        logger.warn("In ShowPremiumCarsCommand command");
        Router router=new Router();
        HttpSession session= request.getSession();
        CarServiceImpl carService=CarServiceImpl.getInstance();
        List<Car> premiumCars;
        try{
            premiumCars=carService.showPremiumCar();
            session.setAttribute(AttributeName.CARS,premiumCars);
            router.setRedirect();
            router.setPage(PagePath.MAINPAGE);
        }
        catch (ServiceException e){
            logger.error("ShowPremiumCarsCommand command error. {} ",e.getMessage());
            throw new CommandException("ShowPremiumCarsCommand command error.",e);
        }
        return router;
    }
}
