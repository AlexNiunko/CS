package com.epam.cs.command.impl;

import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToRegistrationPage implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router=new Router();
        router.setPage(PagePath.GET_ACTIVATION_CODE);
        return router;
    }
}
