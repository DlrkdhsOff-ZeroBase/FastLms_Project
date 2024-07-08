package com.zero.fastlms.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    public static boolean equals(String plaintext, String hashed) {

        if (plaintext == null || plaintext.isEmpty()) {
            return false;
        }

        return BCrypt.checkpw(plaintext, hashed);
    }

    public static String encPassword(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return "";
        }
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
}
