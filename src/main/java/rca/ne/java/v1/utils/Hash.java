package rca.ne.java.v1.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Hash {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password){
        System.out.println("Password : " + password);
        return bCryptPasswordEncoder.encode(password);
    }
    public static boolean isTheSame(String rawPassword , String savedPassword){

        return bCryptPasswordEncoder.matches(rawPassword , savedPassword);
    }

}
