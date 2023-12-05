package com.jwtTokenAuth.service;

import org.springframework.stereotype.Service;

import com.jwtTokenAuth.dto.AuthRequest;
import com.jwtTokenAuth.dto.RegisterRequest;
import com.jwtTokenAuth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepo;

	public String authenticate(AuthRequest authRequest) {
		
		return null;
	}

	public String register(RegisterRequest request) {
		
		return null;
	}

}
