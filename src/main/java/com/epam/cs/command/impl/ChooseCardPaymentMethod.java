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

public class ChooseCardPaymentMethod implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        router.setPage(PagePath.ORDERS_PAGE_ADD_CREDIT_CARD);
        return router;
    }
}
