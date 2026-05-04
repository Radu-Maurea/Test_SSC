/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.dsa_cript;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.HexFormat;
import javax.crypto.spec.IvParameterSpec;
/**
 *
 * @author radum
 */
public class DSA_Cript {
    
    public static SecretKey Generate()
            throws Exception
    {
        char[] password = "short_password".toCharArray();
        byte[] salt = new byte[16];
        int iteration_count = 100;
        int key_size = 64;
        SecureRandom myPRNG = new SecureRandom();
        myPRNG.nextBytes(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec pbekSpec = new PBEKeySpec(password, salt, iteration_count,key_size);
        SecretKey myAESPBKey = new SecretKeySpec(keyFactory.generateSecret(pbekSpec).getEncoded(), "DES");
        
        return myAESPBKey;
        
    }
    public static void main(String[] args)
        throws Exception
    {
        SecureRandom myPRNG = new SecureRandom();
        byte[] ivBytes = new byte[8];
        myPRNG.nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        
        SecretKey myKey = Generate();
        Cipher myDES = Cipher.getInstance("DES/CBC/PKCS5Padding");
        
        byte[] plaintext = new byte[18];
        
        myDES.init(Cipher.ENCRYPT_MODE,myKey,ivSpec);
        byte[] ciphertext = myDES.doFinal(plaintext);
        System.out.println("plaintext:"+HexFormat.of().formatHex(plaintext));
        System.out.println("plaintext:"+HexFormat.of().formatHex(ciphertext));
        
        myDES.init(Cipher.DECRYPT_MODE,myKey,ivSpec);
        byte[] dec_plain = myDES.doFinal(ciphertext);
        System.out.println("plaintext:"+HexFormat.of().formatHex(dec_plain));
        
        
        
    }
}
