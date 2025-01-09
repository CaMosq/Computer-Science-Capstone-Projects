/**
 * Grocery Tracker Backend Application
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve the data.
 * Description - Class: This class implements the configurations for spring web security and CORS
 */
package com.carmen.GroceryTracker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    //Disable spring security and cors for development only.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        (requests) -> requests
                                .anyRequest()
                                .permitAll()
                )
                .csrf().disable();  // Disable CSRF for development

        return http.build();
    }

    //configure cors globally
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")  // Allow Angular frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }

}