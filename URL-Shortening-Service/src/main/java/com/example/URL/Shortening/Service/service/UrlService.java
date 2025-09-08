package com.example.URL.Shortening.Service.service;

import com.example.URL.Shortening.Service.DTO.CreateUrlRequest;
import com.example.URL.Shortening.Service.DTO.ShortUrlResponse;
import com.example.URL.Shortening.Service.DTO.UrlStatsResponse;
import com.example.URL.Shortening.Service.entity.UrlMapping;
import org.springframework.stereotype.Service;

@Service
public interface UrlService {

    ShortUrlResponse createsShortUrl(CreateUrlRequest urlRequest);
    UrlMapping resolve(String shortCode);
    UrlStatsResponse stats(String shortCode);

    UrlMapping findByShortCode(String code);
}
