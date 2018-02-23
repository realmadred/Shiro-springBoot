package com.example.demo;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.util.StopWatch;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RandomTest {
    //Always use a SecureRandom generator
    private static SecureRandom sr;
    //Create array for salt
    private static final byte[] salt = new byte[6];

    static {
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static String getSalt()
    {
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return HexUtils.toHexString(salt);
//        return Base64.getEncoder().encodeToString(salt);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100_0000; i++) {
            getSalt();
        }
        stopWatch.stop();
        System.out.println(stopWatch);
        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());
        System.out.println(getSalt());
    }
}
