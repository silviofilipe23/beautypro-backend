package br.com.beautypro.util;

import org.apache.commons.codec.binary.Base64;
import java.security.SecureRandom;

public class TokenUtil {

    private static final int TOKEN_LENGTH = 32;
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return Base64.encodeBase64URLSafeString(randomBytes);
    }

    public static boolean validateToken(String tokenFromUser, String tokenFromDB) {
        System.out.println(tokenFromUser);
        System.out.println(tokenFromDB);
        return tokenFromUser.equals(tokenFromDB);
    }
}
