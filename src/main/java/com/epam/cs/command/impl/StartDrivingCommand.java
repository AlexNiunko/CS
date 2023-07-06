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
import com.epam.cs.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.routing.Route;

public class StartDrivingCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        HttpSession session=request.getSession();
        Order order= (Order) session.getAttribute(AttributeName.ORDER);
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        try{
            if (orderService.startMoving(order)){
                session.removeAttribute(AttributeName.ORDER);
                session.setAttribute(AttributeName.ORDER,order);
                router.setRedirect();
                router.setPage(PagePath.ORDER_PAGE_MOVING);
            } else {
                logger.warn("Can not to start moving");
                router.setPage(PagePath.ORDER_PAGE_READY_TO_START);
            }
        } catch (ServiceException e){
            logger.error("Failed to start moving");
            throw new CommandException(e);
        }
        return router;
    }
}
