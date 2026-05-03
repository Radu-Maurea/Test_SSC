/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.lab6;
import java.math.BigInteger;
/**
 *
 * @author radum
 */
public class LAB6 {

    public static void main(String[] args) {
        BigInteger n = new BigInteger("837210799");
        BigInteger e = new BigInteger("7");
        BigInteger d = new BigInteger("478341751");
        
        BigInteger four = new BigInteger("4");
        
        BigInteger num = (d.multiply(e)).subtract(BigInteger.ONE);
        BigInteger k = (num.add(n).subtract(BigInteger.ONE)).divide(n);
        System.out.println("K: "+k);
        
        BigInteger p_add_q = ((k.multiply(n.add(BigInteger.ONE))).add(BigInteger.ONE).subtract(e.multiply(d))).divide(k);
        System.out.println("p+q: "+p_add_q);
        
        BigInteger delta = (p_add_q).pow(2).subtract(four.multiply(n));
        System.out.println("delta: "+delta);
        
        BigInteger p = ((p_add_q).add(delta.sqrt())).divide(BigInteger.TWO);
        BigInteger q = ((p_add_q).subtract(delta.sqrt())).divide(BigInteger.TWO);
        
        System.out.println("p:"+p);
        System.out.println("q:"+q);
        
        BigInteger Phi = (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
        BigInteger d_calculat = e.modInverse(Phi);
        System.out.println("d:"+d_calculat);
    }
}
