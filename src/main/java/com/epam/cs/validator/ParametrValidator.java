package com.epam.cs.validator;

import com.epam.cs.command.AttributeName;
import com.epam.cs.command.PagesMessage;
import com.epam.cs.exception.ServiceException;
import com.epam.cs.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.cs.command.AttributeName.*;
import static com.epam.cs.command.PagesMessage.*;

public class ParametrValidator {
    private static final Logger logger = LogManager.getLogger();

    private static ParametrValidator instance = new ParametrValidator();

    private ParametrValidator() {

    }

    public static ParametrValidator getInstance() {
        return instance;
    }
    private boolean validateCardNumber(String input){
        boolean match;
        Pattern pattern=Pattern.compile(ParametrPattern.CARD_NUMBER_PATTERN);
        Matcher matcher= pattern.matcher(input);
        match=matcher.matches();
        return match;
    }
    private boolean validateNameCardHolder(String input){
        boolean match;
        Pattern pattern=Pattern.compile(ParametrPattern.CARD_OWNER_NAME_PATTERN);
        Matcher matcher= pattern.matcher(input);
        match=matcher.matches();
        return match;
    }
    private boolean validateCardExpiryDate(String input){
        boolean match;
        Pattern pattern=Pattern.compile(ParametrPattern.CARD_EXPIRY_DATE_PATTERN);
        Matcher matcher= pattern.matcher(input);
        match=matcher.matches();
        return match;
    }
    private boolean validateCardCvc(String input){
        boolean match;
        Pattern pattern=Pattern.compile(ParametrPattern.CARD_CVC);
        Matcher matcher=pattern.matcher(input);
        match=matcher.matches();
        return match;
    }
    public boolean validateIdCar(String input){
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.ID_CAR_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }
    public boolean validateIdUser(String input){
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.ID_USER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    private boolean validateDrivingLicenseNumber(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.DRIVING_LICENSE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    private boolean validateDateOfExpirity(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.DATE_OF_EXPIRITY_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    private boolean validateNameOrSurname(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.NAME_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validatePassword(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateEmail(String input)  {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match=matcher.matches();
        return match;
    }
    private boolean validateActivationCode(HttpServletRequest request) {
        HttpSession session=request.getSession();
        if (session.getAttribute(ACTIVATION_CODE) == null || request.getParameter(ACTIVATION_CODE)==null) {
             return false;
        } else if (!request.getParameter(ACTIVATION_CODE).matches(ParametrPattern.ACTIVATION_CODE_PATTERN)){
             return false;
        } else if (!request.getParameter(ACTIVATION_CODE).equals(session.getAttribute(ACTIVATION_CODE))){
            return false;
        }else {
            return true;
        }
    }

    public boolean validateAndCorrectInputData(Map<String,String> userInputData,HttpServletRequest request) throws ServiceException {
        boolean isCorrect=true;
        UserServiceImpl userService=UserServiceImpl.getInstance();
        Map<String,String>invalidParameters=new HashMap<>();
        for (Map.Entry<String, String> map:userInputData.entrySet()) {
            String key= map.getKey();
            switch (key){
                case NAME->{
                    if (!validateNameOrSurname(map.getValue())){
                        invalidParameters.put(INCORRECT_NAME,INCORRECT_NAME);
                        isCorrect=false;
                    }
                }
                case SURNAME -> {
                    if (!validateNameOrSurname(map.getValue())){
                        invalidParameters.put(INCORRECT_SURNAME,INCORRECT_SURNAME);
                        isCorrect=false;
                    }
                }
                case DATE_OF_EXPIRITY -> {
                    if (!validateDateOfExpirity(map.getValue())){
                        invalidParameters.put(INCORRECT_DATE_OF_EXPIRITY,INCORRECT_DATE_OF_EXPIRITY);
                        isCorrect=false;
                    }
                }
                case IDENTIFICATION_NUMBER -> {
                    if (!validateDrivingLicenseNumber(map.getValue())){
                        invalidParameters.put(INCORRECT_IDENTIFICATION_NUMBER,INCORRECT_IDENTIFICATION_NUMBER);
                        isCorrect=false;
                    } else if(userService.findIdentificationNumber(map.getValue())){
                        invalidParameters.put(INCORRECT_IDENTIFICATION_NUMBER,IDENTIFICATION_NUMBER_IS_EXISTS);
                        request.setAttribute(AttributeName.IF_USER_EXIST,PagesMessage.USER_IS_EXISTS);
                        logger.warn("A user with the same Email and driver's license already exists");
                        isCorrect=false;
                    }
                }
                case E_MAIL -> {
                    if (!validateEmail(map.getValue())){
                        invalidParameters.put(INCORRECT_EMAIL,INCORRECT_EMAIL);
                        isCorrect=false;
                    } else if (userService.findEmail(map.getValue())){
                        invalidParameters.put(INCORRECT_EMAIL, E_MAIL_IS_EXISTS);
                        request.setAttribute(AttributeName.IF_USER_EXIST,PagesMessage.USER_IS_EXISTS);
                        logger.warn("A user with the same Email and driver's license already exists");
                        isCorrect=false;
                    }
                }
                case PASSWORD -> {
                    if (!validatePassword(map.getValue())){
                        invalidParameters.put(INCORRECT_PASSWORD,INCORRECT_PASSWORD);
                        isCorrect=false;
                    } else if (userService.findPassword(map.getValue())){
                        invalidParameters.put(INCORRECT_PASSWORD,PASSWORD_IS_EXISTS);
                        logger.warn("Current password is already exist");
                        isCorrect=false;
                    }
                }
                case ID_CAR -> {
                    if (!validateIdCar(map.getValue())){
                        isCorrect=false;
                    }
                }
                case USERS_ID -> {
                    if (!validateIdUser(map.getValue())){
                        isCorrect=false;
                    }
                }
                case AGAIN_PASSWORD -> {
                    String password=userInputData.get(PASSWORD);
                    if ( ((!password.equals(map.getValue()))||(!validatePassword(map.getValue())))){
                        invalidParameters.put(INCORRECT_AGAIN_PASSWORD,INCORRECT_AGAIN_PASSWORD);
                        isCorrect=false;
                    }
                }
                case ACTIVATION_CODE -> {
                    if (!validateActivationCode(request)){
                        invalidParameters.put(INCORRECT_ACTIVATION_CODE,INCORRECT_ACTIVATION_CODE);
                        isCorrect=false;
                    }
                }
                case CARD_NUMBER ->{
                    if (!validateCardNumber(map.getValue())){
                        invalidParameters.put(INCORRECT_CARD_NUMBER,INCORRECT_CARD_NUMBER);
                        isCorrect=false;
                    }
                }
                case CARD_EXPIRY_DATE -> {
                    if (!validateCardExpiryDate(map.getValue())){
                        invalidParameters.put(INCORRECT_CARD_EXPIRY_DATE,INCORRECT_CARD_EXPIRY_DATE);
                        isCorrect=false;
                    }
                }
                case CARD_OWNER_NAME -> {
                    if (!validateNameCardHolder(map.getValue())){
                        invalidParameters.put(INCORRECT_CARD_OWNER_NAME,INCORRECT_CARD_OWNER_NAME);
                        isCorrect=false;
                    }
                }
                case CARD_CVC -> {
                    if (!validateCardCvc(map.getValue())){
                        invalidParameters.put(INCORRECT_CARD_CVC,INCORRECT_CARD_CVC);
                        isCorrect=false;
                    }
                }
            }
        }
        userInputData.putAll(invalidParameters);
        return isCorrect;
    }

}
