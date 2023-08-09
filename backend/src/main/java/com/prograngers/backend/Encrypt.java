package com.prograngers.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class Encrypt {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY = "sercretkeyofprograngersbybase641"; // 32byte
    private static final String IV = "prprograngers744"; // 16byte
    private static final String KEY_ALGORITHM = "AES";
    private static final String CHARSET_NAME ="UTF-8";


    // 암호화
    public String encryptAES256(String text) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(CHARSET_NAME));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new FailToEncodeException();
        }

    }

    // 복호화
    public String decryptAES256(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, CHARSET_NAME);
        } catch (Exception e) {
            throw new FailToDecodeException();
        }

    }
}