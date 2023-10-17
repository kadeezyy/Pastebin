package com.example.pastebin.service.hashGenerator;

import com.example.pastebin.dtos.PasteDto;
import com.example.pastebin.dtos.SimplePaste;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

@Service
public class HashGeneratorService {
    private final MessageDigest messageDigest;

    public HashGeneratorService(
            @Value("${spring.security.hashEncoder.algorithmInstance}") String hashEncoder
    ) throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance(hashEncoder);
    }

    @SneakyThrows
    public String generateHash(SimplePaste paste) {
        var salt = createSalt();
        //Add salted bytes to digest
        messageDigest.update(salt);

        //Get the hash's bytes
        byte[] bytes = messageDigest.digest(paste.getText().getBytes());

        //Convert it to hexadecimal format to
        //get complete salted hash in hex format
        return convertToHex(bytes);
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }

    private byte[] createSalt()
            throws NoSuchAlgorithmException,
            NoSuchProviderException {

        //Always use a SecureRandom generator for random salt
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }
}
