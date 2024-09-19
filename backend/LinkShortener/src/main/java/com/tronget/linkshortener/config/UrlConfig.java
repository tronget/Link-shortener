package com.tronget.linkshortener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "short-url", ignoreUnknownFields = false)
@Getter
@Setter
public class UrlConfig {

    private int hashLength = 7;
    private String hashAlgorithm = "SHA-256";
}
