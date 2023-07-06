package com.epam.cs.command.impl;

import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToUsersPage implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException, DaoException {
        Router router=new Router();
        router.setRedirect();
        router.setPage(PagePath.USER_PAGE);
        return router;
    }
}
