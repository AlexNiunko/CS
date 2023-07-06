package com.epam.cs.command;

import com.epam.cs.exception.CommandException;
import com.epam.cs.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request) throws CommandException, DaoException;

}
