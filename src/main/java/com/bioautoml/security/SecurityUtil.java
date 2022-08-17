package com.bioautoml.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {

    public static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String encode(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public static Boolean compareEncode(String inComingString, String encodeString){
        return bCryptPasswordEncoder.matches(inComingString, encodeString);
    }

}
