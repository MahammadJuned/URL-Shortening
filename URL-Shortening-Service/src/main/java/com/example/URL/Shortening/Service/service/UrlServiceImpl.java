package com.example.URL.Shortening.Service.service;

import com.example.URL.Shortening.Service.DTO.CreateUrlRequest;
import com.example.URL.Shortening.Service.DTO.ShortUrlResponse;
import com.example.URL.Shortening.Service.DTO.UrlStatsResponse;
import com.example.URL.Shortening.Service.entity.UrlMapping;
import com.example.URL.Shortening.Service.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{
    private final UrlMappingRepository repository;

    private final String baseUrl = "http://localhost:8080";
    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String encodeBase62(long id) {
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            shortUrl.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return shortUrl.toString();
    }

    private void validateUrl(String url) {
        try {
            URI u = URI.create(url);
            if (u.getScheme() == null || !(u.getScheme().equalsIgnoreCase("http") || u.getScheme().equalsIgnoreCase("https"))) {
                throw new IllegalArgumentException("URL must start with http:// or https://");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }
    @Override
    public ShortUrlResponse createsShortUrl(CreateUrlRequest urlRequest) {
        validateUrl(urlRequest.getOriginalUrl());

        LocalDateTime expiration = null;
        if(urlRequest.getTtlSeconds() != null && urlRequest.getTtlSeconds() > 0) {
            expiration = LocalDateTime.now().plusSeconds(urlRequest.getTtlSeconds());
        }
        UrlMapping entity = UrlMapping.builder()
                .originalUrl(urlRequest.getOriginalUrl())
                .expiresAt(expiration)
                .build();
        entity = repository.save(entity);

        String code = encodeBase62(entity.getId());
        entity.setShortCode(code);
        entity = repository.save(entity);

        return ShortUrlResponse.builder()
                .originalUrl(entity.getOriginalUrl())
                .shortCode(code)
                .shortUrl(baseUrl+"/" + code)
                .expiration(entity.getExpiresAt())
                .build();
    }

    @Override
    public UrlMapping resolve(String shortCode) {
        UrlMapping urlMapping = repository.findByShortCode(shortCode)
                .orElseThrow(()-> new IllegalArgumentException("Short code not found"));
        if(urlMapping.getExpiresAt() != null && LocalDateTime.now().isAfter(urlMapping.getExpiresAt())) {
            throw new IllegalArgumentException("Short code might expires");
        }
        urlMapping.setClickCount(urlMapping.getClickCount()+1);
        repository.save(urlMapping);
        return urlMapping;
    }

    @Override
    public UrlStatsResponse stats(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(()-> new IllegalArgumentException("Shortcode not found"));
        return UrlStatsResponse.builder()
                .originalUrl(mapping.getOriginalUrl())
                .shortCode(mapping.getShortCode())
                .expiresAt(mapping.getExpiresAt())
                .clickCount(mapping.getClickCount())
                .createdAt(mapping.getCreatedAt())
                .build();
    }

    @Override
    public UrlMapping findByShortCode(String code) {
        UrlMapping mapping = repository.findByShortCode(code)
                .orElseThrow(()-> new IllegalArgumentException("Shortcode not found"));
        return mapping;
    }
}
