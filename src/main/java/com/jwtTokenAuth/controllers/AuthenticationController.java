package com.jwtTokenAuth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtTokenAuth.dto.AuthRequest;
import com.jwtTokenAuth.dto.RegisterRequest;
import com.jwtTokenAuth.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest){
		return ResponseEntity.ok().body(authService.authenticate(authRequest));
	}
}
