package com.tronget.linkshortener.util;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class HashGenerator {

    public String generateHash(

            @NonNull String text,
            int hashLength,
            @NonNull String hashAlgorithm

    ) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        byte[] hash = digest.digest(bytes);
        String encodedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        return encodedHash.substring(0, hashLength);
    }

}
