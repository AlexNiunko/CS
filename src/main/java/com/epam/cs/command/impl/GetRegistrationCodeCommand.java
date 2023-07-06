package com.epam.cs.command.impl;


import com.epam.cs.command.*;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.mail.MailSender;
import com.epam.cs.service.UserService;
import com.epam.cs.service.impl.UserServiceImpl;
import com.epam.cs.util.ActivationCodeGenerator;
import com.epam.cs.validator.ParametrValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetRegistrationCodeCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.warn("in get registration code command");
        Router router=new Router();
        String page;
        HttpSession session= request.getSession();
        String activationCode;
        Map<String,String> userInputData=new HashMap<>();
        userInputData.put(AttributeName.NAME,request.getParameter(AttributeName.NAME));
        userInputData.put(AttributeName.SURNAME,request.getParameter(AttributeName.SURNAME));
        userInputData.put(AttributeName.DATE_OF_EXPIRITY,request.getParameter(AttributeName.DATE_OF_EXPIRITY));
        userInputData.put(AttributeName.IDENTIFICATION_NUMBER,request.getParameter(AttributeName.IDENTIFICATION_NUMBER));
        userInputData.put(AttributeName.E_MAIL,request.getParameter(AttributeName.E_MAIL));
        userInputData.put(AttributeName.PASSWORD,request.getParameter(AttributeName.PASSWORD));
        userInputData.put(AttributeName.AGAIN_PASSWORD,request.getParameter(AttributeName.AGAIN_PASSWORD));
        UserService userService= UserServiceImpl.getInstance();
        ParametrValidator validator=ParametrValidator.getInstance();
        try {
            if(validator.validateAndCorrectInputData(userInputData,request) ){
                    activationCode= ActivationCodeGenerator.generateCode();
                    MailSender.sendActivationCodeByEmail(request,activationCode);
                    session.setAttribute(AttributeName.ACTIVATION_CODE,activationCode);
                    request.setAttribute(AttributeName.SEND_MESSAGE,PagesMessage.SEND_ACTIVATION_CODE);
                    page = PagePath.REGISTRATION;
                    router.setPage(page);
                for (Map.Entry<String,String> map:userInputData.entrySet()) {
                    request.setAttribute(map.getKey(), map.getValue());
                }
            } else {
                for (Map.Entry<String,String> map:userInputData.entrySet()) {
                    request.setAttribute(map.getKey(), map.getValue());
                }
                logger.warn("Incorrect inputData");
                page = PagePath.GET_ACTIVATION_CODE;
                router.setPage(page);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }


}
