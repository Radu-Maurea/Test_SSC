/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rsa_encryption;

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
public class RSA_Encryption {

    public static void main(String[] args) {
        try{
            Cipher myRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            KeyPairGenerator myRSAKeyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom myPRNG = new SecureRandom();
            
            myRSAKeyGen.initialize(1024,myPRNG);
            KeyPair myRSAKeyPair = myRSAKeyGen.generateKeyPair();
            
            Key pbKey = myRSAKeyPair.getPublic();
            Key pvKey = myRSAKeyPair.getPrivate();
            
            myRSA.init(Cipher.ENCRYPT_MODE,pbKey,myPRNG);
            byte[] plaintext = "Un m?esaj secret".getBytes();
            byte[] ciphertext = myRSA.doFinal(plaintext);
            
            System.out.println("Criptotext:"+HexFormat.of().formatHex(ciphertext));
            
            myRSA.init(Cipher.DECRYPT_MODE,pvKey,myPRNG);
            byte[] dec_plaintext = new byte[plaintext.length];
            
            dec_plaintext = myRSA.doFinal(ciphertext);
            System.out.println("Decriptat: "+ new String(dec_plaintext));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        
    }
}
