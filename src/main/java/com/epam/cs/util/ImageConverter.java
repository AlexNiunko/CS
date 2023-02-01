package com.epam.cs.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;

public class ImageConverter {
    private static final Logger logger = LogManager.getLogger();
    private static final String CHARSET_NAME = "UTF-8";


    private ImageConverter(){}
    
    public static String imageToString(byte[] imageBytes) {
        String base64Encoded = null;
        try {
            byte[] encodeBase64 = Base64.encodeBase64(imageBytes);
            base64Encoded = new String(encodeBase64, CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARN,"ImageConverter imageToString can't be reached. {}",e.getMessage());
        }
        return base64Encoded;
    }

}
