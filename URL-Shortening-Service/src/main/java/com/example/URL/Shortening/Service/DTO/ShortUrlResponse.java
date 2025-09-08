package com.example.URL.Shortening.Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortUrlResponse {

    private String shortUrl;
    private String shortCode;
    private String originalUrl;
    private LocalDateTime expiration;
}
