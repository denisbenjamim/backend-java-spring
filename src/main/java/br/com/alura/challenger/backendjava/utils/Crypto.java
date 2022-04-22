package br.com.alura.challenger.backendjava.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Crypto {
    private MessageDigest md;

    public Crypto(String seed) {
        try {
            md = MessageDigest.getInstance("SHA-512");
            byte[] salt = new byte[16];
            SecureRandom secureRandom = new SecureRandom(seed.getBytes());
            secureRandom.nextBytes(salt);
            md.update(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getHashSenha(String senha){
        byte[] hashedPassword = md.digest(senha.getBytes(StandardCharsets.ISO_8859_1));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
    
    public boolean compararSenhaComHash(String senha, String hashSenha){
        return hashSenha.equals(getHashSenha(senha));
    }
}
