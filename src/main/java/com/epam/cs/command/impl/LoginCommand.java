package com.epam.cs.command.impl;


import com.epam.cs.command.*;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.UserService;
import com.epam.cs.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        HttpSession session=request.getSession();
        String login=request.getParameter(AttributeName.E_MAIL);
        String password=request.getParameter(AttributeName.PASSWORD);
        UserService userService= UserServiceImpl.getInstance();
        String page;
        try {
            if( (userService.authenticate(login,password)) ){
                request.setAttribute(PagesMessage.USER,login);
                session.setAttribute(PagesMessage.USER,login);
                page = PagePath.MAIN;
                router.setRedirect();
                router.setPage(page);
            } else {
                request.setAttribute(PagesMessage.LOGIN_MSG,"incorrect login or password");
                page = PagePath.MAIN;
                router.setPage(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
