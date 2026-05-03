/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.generare;

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
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import java.util.HexFormat;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author radum
 */
public class Generare {
    
    public static byte[] GetKey()
            throws Exception
    {
        char[] parola = "parola".toCharArray();
        byte[] salt = new byte[16];
            
        int iteration_count = 10000,key_size = 128;
        SecureRandom myPRNG = new SecureRandom();
        myPRNG.nextBytes(salt);
            
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec pbekSpec = new PBEKeySpec(parola,salt,iteration_count,key_size);
        SecretKey myAESPBKey = new SecretKeySpec(keyFactory.generateSecret(pbekSpec).getEncoded(),"AES");
            
        System.out.println("AES key: "+HexFormat.of().formatHex(myAESPBKey.getEncoded()));
        return myAESPBKey.getEncoded();
    }
    public static void main(String[] args)
            throws Exception
    {
        byte[] keyBytes = GetKey();
        SecretKeySpec myKey = new SecretKeySpec(keyBytes,"AES");
        
        String plaintext = "Masdesaaasdasfcsaj secret";
        byte[] textBytes = plaintext.getBytes();
        byte[] criptotext = new byte[32];
        
        Cipher myAES = Cipher.getInstance("AES/ECB/PKCS5Padding");
        myAES.init(Cipher.ENCRYPT_MODE,myKey);
        
        int cLength = myAES.update(textBytes,0,textBytes.length,criptotext,0);
        cLength += myAES.doFinal(criptotext,cLength);
        
        System.out.println("plaintext:"+plaintext);
        System.out.println("plain hex:"+HexFormat.of().formatHex(textBytes));
        System.out.println("criptotext:"+HexFormat.of().formatHex(criptotext));
        
        myAES.init(Cipher.DECRYPT_MODE,myKey);
        byte[] dec_plain = new byte[textBytes.length];
        cLength = myAES.update(criptotext,0,criptotext.length,dec_plain,0);
        cLength += myAES.doFinal(dec_plain,cLength);
        
        System.out.println("decriptat:"+HexFormat.of().formatHex(dec_plain));
        
        
        
    }
}
