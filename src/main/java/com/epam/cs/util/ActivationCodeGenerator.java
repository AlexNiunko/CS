package com.epam.cs.util;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.UUID;

public class ActivationCodeGenerator {
    private static final int ACTIVATION_CODE_LENGTH = 6;
    private static final Logger logger = LogManager.getLogger();

    public  static String generateCode(){
        String activationCode = null;
        activationCode=UUID.randomUUID().toString();
        return activationCode.substring(0, ACTIVATION_CODE_LENGTH);
    }
}
