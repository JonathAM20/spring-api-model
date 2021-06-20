package br.com.domain.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class CriptografiaAES {

    static String IV = "AAAAAAAAAAAAAAAA";
    static String chaveencriptacao = "0123456789abcdef";

    public static byte[] encrypt(String textopuro) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

//        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] textoencriptado) throws Exception{
        Security.addProvider(new BouncyCastleProvider());

//        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado),"UTF-8");
    }
}