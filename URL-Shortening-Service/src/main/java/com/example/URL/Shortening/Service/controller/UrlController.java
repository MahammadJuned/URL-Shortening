package com.example.URL.Shortening.Service.controller;

import com.example.URL.Shortening.Service.DTO.CreateUrlRequest;
import com.example.URL.Shortening.Service.DTO.ShortUrlResponse;
import com.example.URL.Shortening.Service.DTO.UrlStatsResponse;
import com.example.URL.Shortening.Service.DTO.UserResponseDTO;
import com.example.URL.Shortening.Service.entity.UrlMapping;
import com.example.URL.Shortening.Service.repository.UrlMappingRepository;
import com.example.URL.Shortening.Service.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;


    @PostMapping("/create")
    @Operation(summary = "Create short Url for long urls",
            description = "Enter the Original Url link along with the expiration time in seconds")
    @ApiResponses(value = {@ApiResponse(responseCode="201",
            description = "Creates an unique short url",
            content=@Content(mediaType = "application/json",
                    schema=@Schema(implementation = ShortUrlResponse.class)))

    })
    public ResponseEntity<ShortUrlResponse> create(@Valid @RequestBody CreateUrlRequest request) {
        ShortUrlResponse response = urlService.createsShortUrl(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    @Operation(summary = "Redirect short code",
            description = "Please enter the shortcode. In real usage, this API redirects to the original URL, but in Swagger UI it returns the same ShortUrlResponse as the /create API.")
    @ApiResponses(value = {@ApiResponse(responseCode="200",
            description = "Creates an unique short url",
            content=@Content(mediaType = "application/json",
                    schema=@Schema(implementation = ShortUrlResponse.class)))

    })
    public ResponseEntity<?> redirect(@Parameter(description = "Short URL code", required = true, example = "abc123") @PathVariable String code, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        boolean fromSwagger = (referer != null && referer.contains("swagger"));

        if (fromSwagger) {
            // Fetch without increment
            UrlMapping mapping = urlService.findByShortCode(code);
            String baseUrl = "http://localhost:8080/" + mapping.getShortCode();
            return ResponseEntity.ok(new ShortUrlResponse(
                    baseUrl,
                    mapping.getShortCode(),
                    mapping.getOriginalUrl(),
                    mapping.getExpiresAt()
            ));
        } else {
            // Real redirect → increment
            UrlMapping mapping = urlService.resolve(code); // increments clickCount
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(mapping.getOriginalUrl()))
                    .build();
        }
    }


    @GetMapping("/{code}/stats")
    @Operation(summary = "Get statistics for a short URL",
            description = "Provide a shortcode (e.g., `abc123`) to retrieve analytics about the shortened URL. "
                    + "The response includes the original URL, shortcode, creation date, expiration date, "
                    + "and the total click count. If the shortcode does not exist, a `404 Not Found` error is returned.")
    @ApiResponses(value = {@ApiResponse(responseCode="200",
            description = "show stats",
            content=@Content(mediaType = "application/json",
                    schema=@Schema(implementation = UrlStatsResponse.class)))

    })
 public UrlStatsResponse stats(@Parameter(description = "Short URL code", required = true, example = "abc123") @PathVariable("code") String code) {

            try {
                return urlService.stats(code);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shortcode not found: " + code);
            }
        }

    }

