package com.zup.propostas.utils;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Criptografia {

    AES256TextEncryptor encryptor = new AES256TextEncryptor();


    public String encode(String elementoEncode){
        encryptor.setPassword("${criptografia.sucurity.password}");
        String textoEncode = encryptor.encrypt(elementoEncode);
        return textoEncode;
    }

    public String decode(String elementoDecode){
        encryptor.setPassword("${criptografia.sucurity.password}");
        String textoDecode = encryptor.decrypt(elementoDecode);
        return textoDecode;
    }

    public static String getHashSHA512(String entrada){
        Assert.hasText(entrada, "Texto nao pode estar em branco");
        String toReturn = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(entrada.getBytes(StandardCharsets.UTF_8));
            toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toReturn;
    }



}
