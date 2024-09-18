package com.tronget.linkshortener.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NonExistingHashedUrlException extends Exception {
    private String hashedUrl;

    @Override
    public String getMessage() {
        return "Hashed url '" + hashedUrl + "' does not exist.";
    }
}
