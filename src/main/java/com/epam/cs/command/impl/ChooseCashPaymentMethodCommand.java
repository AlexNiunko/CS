package com.epam.cs.command.impl;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.entity.Car;
import com.epam.cs.entity.Order;
import com.epam.cs.entity.User;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Enumeration;
import java.util.Optional;
import java.util.stream.Stream;

public class ChooseCashPaymentMethodCommand implements Command {
    private static final String CASH_METHOD="cash";
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session= request.getSession();
        Router router=new Router();
        Order order=new Order();
        logger.warn("result to search order");
        User user=(User)session.getAttribute(AttributeName.USER);
        Car car=(Car)session.getAttribute(AttributeName.ORDERS_CAR);
        String idUser= String.valueOf(user.getId());
        String idCar= String.valueOf(car.getId());
        order.setMethod(CASH_METHOD);
        OrderServiceImpl orderService=OrderServiceImpl.getInstance();
        try {
                if (orderService.addPreOrderWithCash(idCar, idUser, order)) {
                    logger.warn("Added pre-order,chosed cash payment method");
                    session.setAttribute(AttributeName.ORDER, order);
                    session.setAttribute(AttributeName.PAYMENT_METHOD, order.getMethod().toString());
                    router.setRedirect();
                    router.setPage(PagePath.ORDER_PAGE_READY_TO_START);
                } else {
                    logger.warn("Failed to add pre-order when chosed payment mthod");
                    router.setPage(PagePath.ORDERS_PAGE_PAYMENT_METHOD);
                }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
