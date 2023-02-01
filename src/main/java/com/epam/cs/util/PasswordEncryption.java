package com.epam.cs.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
    private static final Logger logger = LogManager.getLogger();
    private static final String ALGORITHM="MD5";
    private static final String CHARSET_NAME="utf8";
    public static String encrypt(String password) {
        MessageDigest messageDigest;
        byte [] byteEncoded;
        try {
            messageDigest=MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(password.getBytes(CHARSET_NAME));
            byteEncoded= messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.warn(e.getMessage(),"Impossible Exception");
            throw new RuntimeException();
        }
        BigInteger bigInt=new BigInteger(1,byteEncoded);
        String resHex= bigInt.toString(16);
        return resHex;
    }

}
