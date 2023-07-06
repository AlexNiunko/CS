package com.epam.cs.command;


import com.epam.cs.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    ADD_NEW_USER(new AddNewUserCommand()),
    ADD_CREDIT_CARD_COMMAND(new AddCreditCardCommand()),
    GET_ACTIVATION_CODE_COMMAND(new GetRegistrationCodeCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    CHANGE_CAR_COMMAND(new ChangeTheCarCommand()),
    CHOOSE_CASH_PAYMENT_METHOD(new ChooseCashPaymentMethodCommand()),
    CHANGE_PAYMENT_METHOD(new ChangePaymentMethodCommand()),
    DEFAULT(new DefaultCommand()),
    REGISTER(new RegisterCommand()),
    ALL_CARS(new ShowAvailableCarsCommand()),
    ALL_FREE_AVAILABLE_CARS(new ShowFreeAndAvailableCars()),
    FREE_AVAILABLE_BUSINESS_CARS(new ShowFreeAndAvailableBusinessCars()),
    FREE_AVAILABLE_ECONOMY_CARS(new ShowFreeAndAvailableEconomyCars()),
    FREE_AVAILABLE_PREMIUM_CARS(new ShowFreeAndAvailablePremiumCars()),
    FREE_AVAILABLE_STATION_WAGON_CARS(new ShowFreeAndAvailableStationWagonCars()),
    ECONOMY(new ShowEconomyCars()),
    PREMIUM(new ShowPremiumCars()),
    BUSINESS(new ShowBusinessCars()),
    STATION_WAGON(new ShowStationWagonCars()),
    GO_TO_LOGIN(new GoToLoginPage()),
    GO_TO_REGISTRATION_PAGE(new GoToRegistrationPage()),
    GO_TO_USER_PAGE(new GoToUsersPage()),
    CHOOSE_CAR_COMMAND(new ChooseTheCarCommand()),
    CHOOSE_CARD_PAYMENT_METHOD(new ChooseCardPaymentMethod()),
    START_DRIVING(new StartDrivingCommand());
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