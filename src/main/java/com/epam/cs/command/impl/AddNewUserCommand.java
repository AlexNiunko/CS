package com.epam.cs.command.impl;

import com.epam.cs.command.*;
import com.epam.cs.entity.User;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.mail.MailSender;
import com.epam.cs.service.UserService;
import com.epam.cs.service.impl.UserServiceImpl;
import com.epam.cs.util.ActivationCodeGenerator;
import com.epam.cs.validator.ParametrValidator;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddNewUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException{
        logger.warn("Into add new user Command");
        Router router = new Router();
        String page;
        HttpSession session = request.getSession();
        Map<String, String> userInputData = new HashMap<>();
        userInputData.put(AttributeName.NAME, request.getParameter(AttributeName.NAME));
        userInputData.put(AttributeName.SURNAME, request.getParameter(AttributeName.SURNAME));
        userInputData.put(AttributeName.DATE_OF_EXPIRITY, request.getParameter(AttributeName.DATE_OF_EXPIRITY));
        userInputData.put(AttributeName.IDENTIFICATION_NUMBER, request.getParameter(AttributeName.IDENTIFICATION_NUMBER));
        userInputData.put(AttributeName.E_MAIL, request.getParameter(AttributeName.E_MAIL));
        userInputData.put(AttributeName.PASSWORD, request.getParameter(AttributeName.PASSWORD));
        userInputData.put(AttributeName.AGAIN_PASSWORD, request.getParameter(AttributeName.AGAIN_PASSWORD));
        userInputData.put(AttributeName.ACTIVATION_CODE,request.getParameter(AttributeName.ACTIVATION_CODE));
        logger.warn("Into add new user Command");
        UserService userService = UserServiceImpl.getInstance();
        try {
            if (userService.addUser(userInputData,request)) {
                Optional<User>optionalUser=userService.findUserByEmail(userInputData);
                if (optionalUser.isPresent()){
                    User user=optionalUser.get();
                    session.setAttribute(AttributeName.USER_WELCOME,PagesMessage.WELCOME+user.getName()+PagesMessage.PLEASE_LOGIN);
                    logger.warn("User has been registered");
                    session.setAttribute(AttributeName.USER,optionalUser.get().getName());
                    session.removeAttribute(AttributeName.ACTIVATION_CODE);
                    router.setRedirect();
                    router.setPage(PagePath.INDEX);
                }else {
                    logger.error("User is not exists in DB!!!!!");
                    router.setPage(PagePath.ERROR_500);
                }
            } else {
                for (Map.Entry<String, String> map : userInputData.entrySet()) {
                    request.setAttribute(map.getKey(), map.getValue());
                }
                logger.warn("Incorrect userinputdata");
                page = PagePath.REGISTRATION;
                router.setPage(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
