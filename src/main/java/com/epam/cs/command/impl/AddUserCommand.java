package com.epam.cs.command.impl;


import com.epam.cs.command.*;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.UserService;
import com.epam.cs.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

public class AddUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        String page;
        HttpSession session= request.getSession();
        Map<String,String> userInputData=new HashMap<>();
        userInputData.put(AttributeName.NAME,request.getParameter(AttributeName.NAME));
        userInputData.put(AttributeName.SURNAME,request.getParameter(AttributeName.SURNAME));
        userInputData.put(AttributeName.DATE_OF_EXPIRITY,request.getParameter(AttributeName.DATE_OF_EXPIRITY));
        userInputData.put(AttributeName.IDENTIFICATION_NUMBER,request.getParameter(AttributeName.IDENTIFICATION_NUMBER));
        userInputData.put(AttributeName.E_MAIL,request.getParameter(AttributeName.E_MAIL));
        userInputData.put(AttributeName.PASSWORD,request.getParameter(AttributeName.PASSWORD));
        UserService userService= UserServiceImpl.getInstance();
        try {
            if( (userService.addUser(userInputData)) ){
                request.setAttribute("user",userInputData.get(AttributeName.NAME));
                router.setRedirect();
                page = PagePath.MAIN;
                router.setPage(page);
            } else {
                for (Map.Entry<String,String> map:userInputData.entrySet()) {
                    request.setAttribute(map.getKey(), map.getValue());
                }
                page = PagePath.REGISTER;
                router.setPage(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }



}
