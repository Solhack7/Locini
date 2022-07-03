package com.springsecurity.ws.Utility;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public String generateStringId(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return new String(returnValue);
    }
    public String ConvertDateToStr(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        return  strDate;
    }
    public int LoopForAutoSize(int legnth) {
        int helpers=0;
        for (int i = 0; i < legnth; i++) {
            helpers=i;
        }
        return helpers;
    }
    public Date getDateNow() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date date = new Date();
        Date dateHelpers = formatter6.parse(formatter.format(date));
        return dateHelpers;
    }
}
