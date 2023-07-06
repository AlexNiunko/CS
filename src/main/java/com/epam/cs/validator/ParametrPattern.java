package com.epam.cs.validator;

public class ParametrPattern {

     public static final String DRIVING_LICENSE_NUMBER_PATTERN = "[0-9]?[A-Z]{2}[0-9]{6}";
     public static final String DATE_OF_EXPIRITY_PATTERN = "(20[0-9][0-9])-?(0[1-9]|1[012])-?(0[1-9]|[12][0-9]|3[01])";
     public static final String NAME_PATTERN = "^[A-Za-zА-ЯЁа-яё]{3,20}$";
     public static final String PASSWORD_PATTERN = "^[A-Za-zА-ЯЁа-яё\\d_!@#,\\.]{6,16}$";
     public static final String EMAIL_PATTERN = "[A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~\\|&&[^\\.\")(,:;<>@ \\[\\]\\/\\s]]((\\.?[A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~|&&[^^\\.\")(,:;<>@ \\[ \\] \\ / \\s]]{1,10}){1,6}||([A-Za-z0-9!#$%&'*+-/=?\\^_`\\{\\}~|&&[^^\\.\")(,:;<>@ \\[ \\] \\ / \\s]]{1,61}))@\\w+\\.\\p{Lower}{2,4}";
     public static final String ACTIVATION_CODE_PATTERN = "[a-z0-9]{6}";
     public static final String ID_CAR_PATTERN="[0-9]{1,}";
     public static final String ID_USER_PATTERN="[0-9]{1,}";
     public static final String CARD_NUMBER_PATTERN="[0-9]{4}\\p{Blank}[0-9]{4}\\p{Blank}[0-9]{4}\\p{Blank}[0-9]{4}";
     public static final String CARD_EXPIRY_DATE_PATTERN="(0[1-9]|1[012])/([1-9]{2})";
     public static final String CARD_OWNER_NAME_PATTERN="([A-Z]{2,})\\p{Blank}([A-Z]{2,})";
     public static final String CARD_CVC="[0-9]{3}";




     private ParametrPattern() {
     }
}
