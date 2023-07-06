package com.epam.cs.command.impl;

import com.epam.cs.command.*;
import com.epam.cs.entity.Car;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.CarServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowFreeAndAvailableStationWagonCars implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException, DaoException {
        Router router=new Router();
        HttpSession session= request.getSession();
        CarServiceImpl carService=CarServiceImpl.getInstance();
        List<Car> availableFreeStationWagonCars;
        try{
            availableFreeStationWagonCars=carService.showFreeStationWagonCars();
            if (availableFreeStationWagonCars.isEmpty()){
                session.setAttribute(AttributeName.FREE_CARS, PagesMessage.ALL_CARS_BUSY);
            } else {
                session.setAttribute(AttributeName.FREE_CARS,availableFreeStationWagonCars);
            }
            router.setRedirect();
            router.setPage(PagePath.CHOOSE_A_CAR_PAGE);
        }catch (ServiceException e){
            logger.warn("ShowFreeAndAvailableStationWagonCars command error. {} ",e.getMessage());
            throw new CommandException("ShowFreeAndAvailableStationWagonCars command error",e);
        }
        return router;
    }
}
