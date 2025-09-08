package com.example.URL.Shortening.Service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUrlRequest {
    @NotBlank(message = "Original URL is required")
    @Pattern(
            regexp = "^(https?://).*",
            message = "URL must start with http:// or https://"
    )
    private String originalUrl;
    private Long ttlSeconds;
}
