package com.example.URL.Shortening.Service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "URL Shortening Service",
		version = "1.0",
		description = "This is REST API for URL Shortening method"
),
		servers = {
				@Server(url = "http://localhost:8080", description = "localServer")
		})
public class UrlShorteningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningServiceApplication.class, args);
	}

}
