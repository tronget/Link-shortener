package com.tronget.linkshortener.service;

import com.tronget.linkshortener.exception.NonExistingHashedUrlException;
import com.tronget.linkshortener.model.UrlEntity;
import com.tronget.linkshortener.repository.UrlRepository;
import com.tronget.linkshortener.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlUtil urlUtil;

    @Autowired
    public UrlService(UrlRepository urlRepository, UrlUtil urlUtil) {
        this.urlRepository = urlRepository;
        this.urlUtil = urlUtil;
    }

    public UrlEntity shorten(String originalUrl) {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByUrl(originalUrl);
        if (urlEntityOptional.isPresent()) {
            return urlEntityOptional.get();
        }

        String hashedUrl = urlUtil.shorten(originalUrl);
        urlEntityOptional = urlRepository.findByHashedUrl(hashedUrl);

        while (urlEntityOptional.isPresent()) {
            hashedUrl = urlUtil.shorten(urlEntityOptional.get().getHashedUrl());
            urlEntityOptional = urlRepository.findByHashedUrl(hashedUrl);
        }

        return UrlEntity.builder().url(originalUrl).hashedUrl(hashedUrl).build();
    }

    public UrlEntity save(UrlEntity urlEntity) {
        return urlRepository.save(urlEntity);
    }

    public UrlEntity findByHashedUrl(String hashedUrl) throws NonExistingHashedUrlException {
        Optional<UrlEntity> urlEntityOptional = urlRepository.findByHashedUrl(hashedUrl);
        return urlEntityOptional.orElseThrow(() -> new NonExistingHashedUrlException(hashedUrl));
    }


}
