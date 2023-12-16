package com.sih.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
   
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       
	   http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
           @Override
           public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
               CorsConfiguration config = new CorsConfiguration();
               config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://ca.thxx.xyz:433"));
               config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000/**", "https://ca.thxx.xyz:433/**"));
               config.setAllowedMethods(Arrays.asList("GET","POST"));
               config.setAllowCredentials(true);
               config.setAllowedHeaders(Collections.singletonList("*"));
               
               config.setMaxAge(3600L); //1시간
               
//               UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//               source.registerCorsConfiguration("/**", config);
               return config;
           }
           }))
	       .csrf((csrfConfig) -> 
	   				csrfConfig.disable()
	   			)
	   			.headers((headerConfig) -> 
	   						headerConfig.frameOptions(frameOptionsConfig ->
	   														frameOptionsConfig.disable()
	   													)
	   					).authorizeHttpRequests((authorizeRequests) -> 
	   						authorizeRequests
	   							.antMatchers("/**").permitAll()
	   							.anyRequest().authenticated()
	   							);
	   return http.build();
   }

}