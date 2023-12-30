package com.sih.config;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
   
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       
	   http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
           @Override
           public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
               CorsConfiguration config = new CorsConfiguration();
               config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://ca.thxx.xyz"));
               config.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000/**", "https://ca.thxx.xyz"));
               config.setAllowedMethods(Arrays.asList("GET","POST"));
               config.setAllowCredentials(true);
               config.setAllowedHeaders(Collections.singletonList("*"));
               
               config.setMaxAge(3600L); //1시간
               
//               UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//               source.registerCorsConfiguration("/**", config);
               return config;
           }
           }));
	   http.csrf((csrfConfig) -> 
	   				csrfConfig.disable()
	   			);
	   
	   http.headers((headerConfig) -> 
	   				headerConfig.frameOptions(frameOptionsConfig ->
	   				frameOptionsConfig.disable()
	   													));
	   http.authorizeHttpRequests((authorizeRequests) -> 
	   						authorizeRequests
	   						    // 로그인, 회원가입 화면은 인증없이 입장가능 
	   							.antMatchers("/**").permitAll()
	   							// 나머지는 인증필요
	   							.anyRequest().authenticated()
	   							);
//	   http.formLogin(form -> form
//               .loginPage("http://localhost:3000/login")
//               .defaultSuccessUrl("/", true)
//               .permitAll()
//       );
//       http.logout(logout -> logout
//               .permitAll());
	   
	   return http.build();
   }
   
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }


}