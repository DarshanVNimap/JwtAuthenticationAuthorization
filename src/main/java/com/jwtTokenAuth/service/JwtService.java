package com.jwtTokenAuth.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final String SECRATE = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
	@Autowired
	private UserDetailsService userDetailsService;

	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode(SECRATE);
		return Keys.hmacShaKeyFor(key);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
					.setSigningKey(getSignKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
	}
	
	public <T> T extractClaim(String token , Function<Claims , T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	public String generateToken(Map<String , Object> claims , UserDetails userDetails) {
		return Jwts.builder()
					.setSubject(userDetails.getUsername())
					.setClaims(claims)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
					.signWith(getSignKey() , SignatureAlgorithm.HS256)
					.compact();
					
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(null, userDetails);
	}
	
	public boolean isValidToken(String token , UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		Date expDate = extractClaim(token , Claims::getExpiration);
		return (expDate.before(new Date()));
	}

	
	
	
}
