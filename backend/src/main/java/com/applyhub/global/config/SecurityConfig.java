package com.applyhub.global.config;

import java.util.List;                                                                                                                                               
import org.springframework.beans.factory.annotation.Value;                                                                                                           
import org.springframework.web.cors.CorsConfigurationSource;                                                                                                         
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.applyhub.auth.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration                                                                                                                                               
@EnableWebSecurity                                                                                                                                           
@RequiredArgsConstructor                                                                                                                                     
public class SecurityConfig {                                                                                                                                
                                                                                                                                                               
    private final JwtAuthenticationFilter jwtAuthenticationFilter;                                                                                           
                                                                                                                                                               
    @Value("${app.cors.allowed-origin}")                                                                                                                     
    private String allowedOrigin;                                                                                                                            
                                                                                                                                                               
    @Bean                                                                                                                                                    
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {                                                                     
        return http                                                                                                                                          
                .csrf(AbstractHttpConfigurer::disable)                                                                                                       
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))                                                                           
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))                                                                                                                                            
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                    "/api/auth/signup",
                    "/api/auth/login", 
                    "/api/health")
                    .permitAll().anyRequest().authenticated())                                                                                                                                            
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)                                                        
                .build();                                                                                                                                    
    }                                                                                                                                                        
                                                                                                                                                               
    @Bean                                                                                                                                                    
    public CorsConfigurationSource corsConfigurationSource() {                                                                                               
        CorsConfiguration configuration = new CorsConfiguration();                                                                                           
        configuration.setAllowedOrigins(List.of(allowedOrigin));                                                                                             
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));                                                        
        configuration.setAllowedHeaders(List.of("*"));                                                                                                       
        configuration.setAllowCredentials(true);                                                                                                             
                                                                                                                                                               
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();                                                                      
        source.registerCorsConfiguration("/**", configuration);                                                                                              
        return source;                                                                                                                                       
    }                                                                                                                                                        
}
