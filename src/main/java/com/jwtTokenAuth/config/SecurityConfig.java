package com.jwtTokenAuth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private  JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	private  AuthenticationProvider authenticationProvider;
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(HttpRequests -> {
	            try {
	                HttpRequests.requestMatchers("/api/v1/auth/**").permitAll()
	                .requestMatchers("/api/v1/u/user").hasRole("USER")
	                .requestMatchers("/api/v1/u/**").hasRole("ADMIN")
	                .anyRequest().authenticated().and()
	                .sessionManagement(sessionManagement -> sessionManagement
	                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                                .authenticationProvider(authenticationProvider)
	                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	                );
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        });

	        return http.build();
	    }
}
