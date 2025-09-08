## Project Report
You can view the detailed project report [here](https://l1nk.dev/3qhfi)
#  URL Shortening Service

A **Spring Boot** application for shortening URLs, handling user authentication with JWT, and tracking statistics for each short URL. The service supports user registration, login, short URL creation, redirection, and stats viewing.  

---

##  Features

-  User Registration & Login with JWT Authentication
-  Secured endpoints using Spring Security
-  Create short URLs with optional expiry date
-  Redirect short code → Original URL
-  Track statistics (click count, createdAt, expiresAt)
-  Swagger UI API documentation

---

##  Tech Stack

- **Java 17+**
- **Spring Boot 3+**
  - Spring Web
  - Spring Data JPA
  - Spring Security + JWT
  - Validation
- **H2 Database** 
- **Swagger** (springdoc-openapi)

---

## 📂 Project Structure
```plaintext
src/main/java/com/example/URL/Shortening/Service
│
├── config
│   └── SecurityConfig.java            # Spring Security configuration
│
├── controller
│   ├── AuthController.java            # Handles login/authentication
│   ├── RegisterController.java        # Handles user registration
│   └── UrlController.java             # Handles URL creation, redirect, stats
│
├── DTO
│   ├── AuthRequestDTO.java            # Login request DTO
│   ├── CreateUrlRequest.java          # Request DTO for creating short URLs
│   ├── ShortUrlResponse.java          # Response DTO for short URL
│   ├── UrlStatsResponse.java          # Response DTO for URL stats
│   ├── UserRequestDTO.java            # Request DTO for user registration
│   └── UserResponseDTO.java           # Response DTO for user info
│
├── entity
│   ├── UrlMapping.java                # Entity for storing short URL data
│   └── UserEntity.java                # Entity for users
│
├── filter
│   └── JwtAuthFilter.java             # JWT filter for authentication
│
├── repository
│   ├── UrlMappingRepository.java      # Repository for UrlMapping
│   └── UserRepository.java            # Repository for UserEntity
│
├── service
│   ├── CustomUserDetailsService.java  # Implements Spring Security UserDetails
│   ├── UrlService.java                # Service interface for URLs
│   └── UrlServiceImpl.java            # Implementation of UrlService
│
├── util
│   └── JwtUtil.java                   # Utility for JWT token generation/validation
│
└── UrlShorteningServiceApplication.java   # Main Spring Boot application

```

**Open Swagger UI** : **http://localhost:8080/swagger-ui.html**

**API Endpoints**

**1.User**

POST /register → Register a new user

**2.Authentication**

POST /log → Authenticate and get JWT token

**3.URL**

POST /create → Create short URL

GET /{code} → Redirect to original URL (or JSON response if called from Swagger)

GET /{code}/stats → Get statistics for a short URL

**Example WorkFlow**

**1.Register User**
<img width="1075" height="654" alt="Screenshot 2025-09-08 123502" src="https://github.com/user-attachments/assets/766b70c4-7fd9-4d76-b81c-2915fe4ace81" />

**2.Login**
<img width="1075" height="668" alt="image" src="https://github.com/user-attachments/assets/bab87f3a-a477-4210-ae4f-cc2d11973efa" />
-Returns JWT Token

**3.Create Short Url**
<img width="1300" height="710" alt="image" src="https://github.com/user-attachments/assets/dcd2a0d5-1b37-4326-9c4c-ebf1e39c7a33" />

**4.Redirect**

Upon clicking the short url you can redirect to the original url webpage

**5.Get Stats**
<img width="1282" height="780" alt="image" src="https://github.com/user-attachments/assets/6b86cebf-3ea8-4d57-b09b-63466040d681" />












