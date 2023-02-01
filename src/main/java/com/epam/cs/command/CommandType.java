package com.epam.cs.command;


import com.epam.cs.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    ADD(new AddUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    REGISTER(new RegisterCommand()),
    MAIN(new MainPageCommand());
    private static Logger logger = LogManager.getLogger();
    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    private Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandStr) {
        logger.warn("In command define");
        CommandType currentCommand;
        Optional<CommandType> ifExist = Arrays.stream(CommandType.values())
                .filter(s -> s == CommandType.valueOf(commandStr))
                .findAny();
        currentCommand = (ifExist.orElse(CommandType.DEFAULT));
        return currentCommand.command;
    }
}
// if (ifExist.isPresent()){
//         currentCommand=CommandType.valueOf(commandStr.toUpperCase());
//         }  else {
//         currentCommand=CommandType.DEFAULT;
//         }

//
//    Optional<String>ifExist= Arrays.stream(CommandType.values())
//            .map(Enum::toString)
//            .filter(s->s.toUpperCase().matches(commandStr.toUpperCase()))
//            .findAny();
//      currentCommand=CommandType.valueOf(ifExist.orElse(String.valueOf(CommandType.DEFAULT)));
//              return currentCommand.command;