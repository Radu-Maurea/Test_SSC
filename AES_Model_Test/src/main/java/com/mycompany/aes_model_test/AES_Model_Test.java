/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.aes_model_test;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author radum
 */
public class AES_Model_Test {

    public static void main(String[] args)
    throws Exception
    {
        char[] password = "short_password".toCharArray();
        byte[] salt = new byte[16];
        int iteration_count = 500;
        int key_size = 128;
        SecureRandom myPRNG = new SecureRandom();
        myPRNG.nextBytes(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec pbekSpec = new PBEKeySpec(password, salt, iteration_count,key_size);
        SecretKey myAESPBKey = new SecretKeySpec(keyFactory.generateSecret(pbekSpec).getEncoded(), "AES");
        
        
        byte[] ivBytes = new byte[16];
        myPRNG.nextBytes(ivBytes);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        Cipher myAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        myAES.init(Cipher.ENCRYPT_MODE,myAESPBKey,iv);
        byte[] plaintext = new byte[35];
        byte[] ciphertext = myAES.doFinal(plaintext);
        
        System.out.println("plain:"+HexFormat.of().formatHex(plaintext));
        System.out.println("cripto:"+HexFormat.of().formatHex(ciphertext));
        
        myAES.init(Cipher.DECRYPT_MODE,myAESPBKey,iv);
        byte[] dec_plain = myAES.doFinal(ciphertext);
        System.out.println("dec:"+HexFormat.of().formatHex(dec_plain));
        
        System.out.println("iv:"+HexFormat.of().formatHex(ivBytes));
        System.out.println("salt:"+HexFormat.of().formatHex(salt));
        
        
        
    }
}
