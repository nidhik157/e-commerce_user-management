package com.example.user;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


	 @Configuration
	 @EnableWebSecurity
	 public class AuthorizeUrlsSecurityConfig {

         @Bean
         SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        	
        	 http.authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll()
        			);
             http.csrf(csrf -> csrf.disable());
	 		return http.build();
	 	}

	}

