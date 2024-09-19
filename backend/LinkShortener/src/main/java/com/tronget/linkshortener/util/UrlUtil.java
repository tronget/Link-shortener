package com.tronget.linkshortener.util;

import com.tronget.linkshortener.config.UrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
@EnableConfigurationProperties(UrlConfig.class)
public class UrlUtil {

    private final UrlConfig urlConfig;
    private final HashGenerator hashGenerator;

    @Autowired
    public UrlUtil(UrlConfig urlConfig, HashGenerator hashGenerator) {
        this.urlConfig = urlConfig;
        this.hashGenerator = hashGenerator;
    }

    public String shorten(String url) {
        int hashLength = urlConfig.getHashLength();
        String hashAlgorithm = urlConfig.getHashAlgorithm();
        String hashedUrl = null;
        try {
            hashedUrl = hashGenerator.generateHash(url, hashLength, hashAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedUrl;
    }

}
