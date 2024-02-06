package com.prograngers.backend.support;

import com.prograngers.backend.exception.unauthorization.FailToDecodeException;
import com.prograngers.backend.exception.unauthorization.FailToEncodeException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Encrypt {

    private static String algorithm;
    private static String key; // 32byte
    private static String iv; // 16byte
    private static String keyAlgorithm;
    private static String charsetName;

    public Encrypt(@Value("${cipher.algorithm}") String algorithm,
                   @Value("${cipher.key}") String key,
                   @Value("${cipher.iv}") String iv,
                   @Value("${cipher.key-algorithm}") String keyAlgorithm,
                   @Value("${cipher.charset-name}") String charsetName) {
        this.algorithm = algorithm;
        this.key = key;
        this.iv = iv;
        this.keyAlgorithm = keyAlgorithm;
        this.charsetName = charsetName;
    }

    // 암호화
    public static String encoding(String text) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), keyAlgorithm);
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes(charsetName));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new FailToEncodeException();
        }

    }

    // 복호화
    public static String decoding(String cipherText) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), keyAlgorithm);
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, charsetName);
        } catch (Exception e) {
            throw new FailToDecodeException();
        }

    }
}