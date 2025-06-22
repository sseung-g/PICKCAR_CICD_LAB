package com.pickcar.filter;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class LogEncryptionHelper {

    private static Cipher cipher;
    private static Key key;

    //FIXME: 환경변수화
    private static final String SECRET_KEY = "1234567890123456";
    private static final String ENCRYPT_ALGORITHM = "AES";

    public LogEncryptionHelper() throws Exception {
        key = new SecretKeySpec(SECRET_KEY.getBytes(), ENCRYPT_ALGORITHM);
        cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
    }

    public static String encrypt(String targetText) {
        String encryptedString = null;

        if (targetText == null || targetText.isBlank()) {
            return null;
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(targetText.getBytes());
            encryptedString = Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedString;
    }

    public static String decrypt(String encryptedText) {
        String decryptedString = null;

        if (encryptedText == null || encryptedText.isBlank()) {
            return null;
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decorded = Base64.getDecoder().decode(encryptedText);
            byte[] decrypted = cipher.doFinal(decorded);
            decryptedString = new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}
