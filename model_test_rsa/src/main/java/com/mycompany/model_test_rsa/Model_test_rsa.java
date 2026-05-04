/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.model_test_rsa;
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
/**
 *
 * @author radum
 */
public class Model_test_rsa {
   
    
    public static void main(String[] args)
        throws Exception
    {
        SecureRandom myPRNG = new SecureRandom();
        Cipher myRSA = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        KeyPairGenerator myRSAKeyGen = KeyPairGenerator.getInstance("RSA");
        myRSAKeyGen.initialize(2048, myPRNG);
        
        KeyPair myRSAKeyPair= myRSAKeyGen.generateKeyPair();
        Key pbKey = myRSAKeyPair.getPublic();
        Key pvKey = myRSAKeyPair.getPrivate();
        
        byte[] plaintext = new byte[16];
        myRSA.init(Cipher.ENCRYPT_MODE, pbKey, myPRNG);
        byte[] ciphertext = myRSA.doFinal(plaintext);
        
        myRSA.init(Cipher.DECRYPT_MODE,pvKey,myPRNG);
        byte[] dec_plain = myRSA.doFinal(ciphertext);
        
        System.out.println("plain:"+HexFormat.of().formatHex(plaintext));
        System.out.println("cipher:"+HexFormat.of().formatHex(ciphertext));
        System.out.println("dec:"+HexFormat.of().formatHex(dec_plain));
        
        
       
    }
}
