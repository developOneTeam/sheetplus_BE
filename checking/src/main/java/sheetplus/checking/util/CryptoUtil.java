package sheetplus.checking.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Component
@Slf4j
public class CryptoUtil {

    @Value("${crypto.algorithm}")
    private String ALGORITHM;

    @Value("${crypto.secret-key}")
    private String SECRET_KEY;


    public Long decrypt(String id){
        try{
            byte[] combined = Base64.getDecoder().decode(URLDecoder.decode(id, StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOfRange(combined, 0, 16);
            byte[] encryptedBytes = Arrays.copyOfRange(combined, 16, combined.length);
            log.info("IV length: {}", iv.length);
            log.info("Encrypted bytes length: {}", encryptedBytes.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = generateKey();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            log.info("Cipher initialized for CBC decryption");

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            log.info("Decryption successful, decrypted bytes length: {}", decryptedBytes.length);

            return ByteBuffer.wrap(decryptedBytes).getLong();
        } catch (Exception e){
            log.error("Decryption failed: ", e);
        }
        return null;
    }


    public String encrypt(Long id){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKey = generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            log.info("Cipher initialized for CBC encryption");

            byte[] valueBytes = ByteBuffer.allocate(Long.BYTES).putLong(id).array();
            byte[] encrypted = cipher.doFinal(valueBytes);
            log.info("Encryption successful, encrypted bytes length: {}", encrypted.length);

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return URLEncoder.encode(Base64.getEncoder().encodeToString(combined), StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error("Encryption failed: ", e);
        }
        return null;
    }

    private SecretKeySpec generateKey(){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("Invalid key length: must be 16, 24, or 32 bytes");
        }
        return new SecretKeySpec(keyBytes, extractBaseAlgorithm(ALGORITHM));
    }

    /**
     * 변환 문자열에서 기본 알고리즘 이름 추출 메서드
     *
     * @param transformation 전체 변환 문자열 (예: "AES/CBC/PKCS5Padding")
     * @return 기본 알고리즘 이름 (예: "AES")
     */
    private String extractBaseAlgorithm(String transformation){
        if(transformation == null || transformation.isEmpty()){
            throw new IllegalArgumentException("Transformation string is null or empty");
        }
        String[] parts = transformation.split("/");
        return parts[0];
    }


}
