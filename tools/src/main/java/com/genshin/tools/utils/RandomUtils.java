package com.genshin.tools.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomUtils {

    public static String createRandomString(){
        SecureRandom random=new SecureRandom();
        return new BigInteger(300,random).toString(32).toUpperCase();
    }
}
