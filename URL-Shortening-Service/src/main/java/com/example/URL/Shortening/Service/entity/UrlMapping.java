package com.example.URL.Shortening.Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "URL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String originalUrl;
    private String shortCode;
    @Builder.Default
    private Long clickCount = 0L;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @PrePersist
    public void assignCreationTime() {
        if(createdAt == null) createdAt=LocalDateTime.now();
    }
}
