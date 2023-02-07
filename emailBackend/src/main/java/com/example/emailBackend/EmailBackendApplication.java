package com.example.emailBackend;

import Controller.ServerController.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackageClasses = LoginController.class)
@ComponentScan(basePackageClasses = SignupController.class)
@ComponentScan(basePackageClasses = ComposeController.class)
@ComponentScan(basePackageClasses = InboxController.class)
@ComponentScan(basePackageClasses = SentController.class)
@ComponentScan(basePackageClasses = DraftController.class)
@ComponentScan(basePackageClasses = InboxController.class)
@ComponentScan(basePackageClasses = ContactsController.class)
@ComponentScan(basePackageClasses = FolderController.class)
@ComponentScan(basePackageClasses = TrashController.class)

public class EmailBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailBackendApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:50234"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}

}
