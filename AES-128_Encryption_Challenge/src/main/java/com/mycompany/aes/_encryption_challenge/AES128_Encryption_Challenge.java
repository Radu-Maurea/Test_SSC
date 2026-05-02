/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.aes._encryption_challenge;
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
public class AES128_Encryption_Challenge {

    public static void main(String[] args) {
        try{
            byte[] keyBytes = new byte[16];
            SecureRandom myPRNG = new SecureRandom();
            myPRNG.nextBytes(keyBytes);
            SecretKeySpec myKey = new SecretKeySpec(keyBytes,"AES");
            
            Cipher myAES = Cipher.getInstance("AES/ECB/PKCS5Padding");
            myAES.init(Cipher.ENCRYPT_MODE, myKey);
            
            byte plaintext[] ={0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0F};
            byte criptotext[] = new byte[16];
            
            int cLength = myAES.update(plaintext,0,plaintext.length,criptotext,0);
            cLength +=  myAES.doFinal(criptotext,cLength);
            
            System.out.println("plaintext: "+HexFormat.of().formatHex(plaintext));
            System.out.println("criptotext: "+HexFormat.of().formatHex(criptotext));
            
            myAES.init(Cipher.DECRYPT_MODE,myKey);
            byte[] dec_plaintext = new byte[plaintext.length];
            
            cLength = myAES.update(criptotext,0,cLength,dec_plaintext,0);
            cLength += myAES.doFinal(dec_plaintext,cLength);
            
            System.out.println("decriptat: "+ HexFormat.of().formatHex(dec_plaintext));
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
