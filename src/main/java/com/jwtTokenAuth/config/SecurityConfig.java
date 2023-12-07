package com.jwtTokenAuth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwtTokenAuth.entity.Permissions;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	private final String adminPath = "/api/v1/admin/**";
	private final String userPath = "/api/v1/user/**";
	
	@Autowired
	private  JwtAuthenticationFilter jwtFilter;
	
	@Autowired
	private  AuthenticationProvider authenticationProvider;
	
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		 http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(HttpRequests -> {
	            try {
	                HttpRequests.requestMatchers("/api/v1/auth/**").permitAll()
	                
	                .requestMatchers(userPath).hasAnyRole("ADMIN" , "USER")
	                
	                .requestMatchers(HttpMethod.GET , userPath).hasAnyAuthority(Permissions.ADMIN_READ.name() , Permissions.USER_READ.name())
	                .requestMatchers(HttpMethod.POST , userPath).hasAnyAuthority(Permissions.ADMIN_CREATE.name() , Permissions.USER_CREATE.name())
	                .requestMatchers(HttpMethod.PUT , userPath).hasAnyAuthority(Permissions.ADMIN_UPDATE.name() , Permissions.USER_UPDATE.name())
	                .requestMatchers(HttpMethod.DELETE , userPath).hasAnyAuthority(Permissions.ADMIN_DELETE.name())
	                
	                .requestMatchers(adminPath).hasRole("ADMIN")
	                
	                .requestMatchers(HttpMethod.GET , adminPath).hasAnyAuthority(Permissions.ADMIN_READ.name())
	                .requestMatchers(HttpMethod.POST , adminPath).hasAnyAuthority(Permissions.ADMIN_CREATE.name())
	                .requestMatchers(HttpMethod.PUT , adminPath).hasAnyAuthority(Permissions.ADMIN_UPDATE.name())
	                .requestMatchers(HttpMethod.DELETE , adminPath).hasAnyAuthority(Permissions.ADMIN_DELETE.name())
	                .anyRequest().authenticated().and()
	                .sessionManagement(sessionManagement -> sessionManagement
	                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                                .authenticationProvider(authenticationProvider)
	                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	                ) ;
	            } catch (Exception e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        });

	        return http.build();
	    }
}
