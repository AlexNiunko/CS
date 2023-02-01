package com.epam.cs.controller;

import java.io.*;
import java.util.ArrayList;

import com.epam.cs.command.Command;
import com.epam.cs.command.CommandType;
import com.epam.cs.command.AttributeName;
import com.epam.cs.entity.Car;
import com.epam.cs.exception.CommandException;
import com.epam.cs.pool.ConnectionPool;
import com.epam.cs.command.Router;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    public void init() {
        ConnectionPool.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
       process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String commandStr = request.getParameter(AttributeName.COMMAND);
            Command command = CommandType.defineCommand(commandStr.toUpperCase());
            Router router=command.execute(request);
            switch (router.getType()){
                case FORWARD -> {
                    request.getRequestDispatcher(router.getPage()).forward(request,response);
                    logger.debug("Forward type");
                }
                case REDIRECT -> {
                    response.sendRedirect(router.getPage());
                    logger.debug("Redirect type");
                }
            }
        } catch (CommandException | IOException e) {
            logger.error("Command error");
            throw new ServletException(e);
        }
    }


    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}