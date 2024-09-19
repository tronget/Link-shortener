package com.tronget.linkshortener.controller;

import com.tronget.linkshortener.config.AppConfig;
import com.tronget.linkshortener.exception.NonExistingHashedUrlException;
import com.tronget.linkshortener.model.UrlEntity;
import com.tronget.linkshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://shrtner.onrender.com")
@EnableConfigurationProperties(AppConfig.class)
public class UrlController {

    private final UrlService urlService;
    private final AppConfig appConfig;

    @Autowired
    public UrlController(UrlService urlService, AppConfig appConfig) {
        this.urlService = urlService;
        this.appConfig = appConfig;
    }


    @PostMapping
    public String addUrl(@RequestBody String url) {
        if (!url.startsWith("http:") && !url.startsWith("https:")) {
            url = "http://" + url;
        }
        UrlEntity entity = urlService.shorten(url);
        urlService.save(entity);
        return appConfig.getBaseUrl() + "/" + entity.getHashedUrl();
    }

    @GetMapping("/{hashedUrl}")
    public RedirectView getUrl(@PathVariable String hashedUrl) {
        String externalUrl = null;

        try {
            externalUrl = urlService.findByHashedUrl(hashedUrl).getUrl();
        } catch (NonExistingHashedUrlException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(externalUrl);
        return redirectView;
    }



}
