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
public class UrlStatsResponse {

    private String originalUrl;
    private String shortCode;
    private Long clickCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
