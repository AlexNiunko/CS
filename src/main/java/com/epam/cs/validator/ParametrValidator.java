package com.epam.cs.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.cs.command.AttributeName.*;

public class ParametrValidator {

    private static ParametrValidator instance = new ParametrValidator();

    private ParametrValidator() {

    }

    public static ParametrValidator getInstance() {
        return instance;
    }

    public boolean validateDrivingLicenseNumber(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.DRIVING_LICENSE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateDateOfExpirity(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.DATE_OF_EXPIRITY_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateNameOrSurname(String input) {
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

    public boolean validateEmail(String input) {
        boolean match;
        Pattern pattern = Pattern.compile(ParametrPattern.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        match = matcher.matches();
        return match;
    }

    public boolean validateAndCorrectInputData(Map<String,String> userInputData){
        boolean isCorrect=true;
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
                        isCorrect=true;
                    }
                }
                case E_MAIL -> {
                    if (!validateEmail(map.getValue())){
                        invalidParameters.put(INCORRECT_EMAIL,INCORRECT_EMAIL);
                        isCorrect=true;
                    }
                }
                case PASSWORD -> {
                    if (!validatePassword(map.getValue())){
                        invalidParameters.put(INCORRECT_PASSWORD,INCORRECT_PASSWORD);
                        isCorrect=true;
                    }
                }
            }
        }
        userInputData.putAll(invalidParameters);
        return isCorrect;
    }


}
