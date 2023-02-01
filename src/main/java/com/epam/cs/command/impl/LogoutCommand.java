package com.epam.cs.command.impl;

import com.epam.cs.command.Command;
import com.epam.cs.command.PagePath;
import com.epam.cs.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) {
        Router router=new Router();
        HttpSession session= request.getSession();
        session.invalidate();
        router.setRedirect();
        router.setPage(PagePath.MAIN);
        return router;
    }
}
