package com.jwtTokenAuth.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtTokenAuth.dto.AuthRequest;
import com.jwtTokenAuth.dto.RegisterRequest;
import com.jwtTokenAuth.entity.Role;
import com.jwtTokenAuth.entity.User;
import com.jwtTokenAuth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepo;
	
	private final ModelMapper modelMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;

	public String register(RegisterRequest request) {

		User user = modelMapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setCreateAt(new Date());
		user.setRole(Role.ROLE_USER);
		
		userRepo.save(user);
		
		return jwtService.generateToken(user);
	}

	public String authenticate(AuthRequest authRequest) throws UserPrincipalNotFoundException {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		
		User user = userRepo.findByEmail(authRequest.getUsername()).orElseThrow(()-> new UserPrincipalNotFoundException("User not found!!"));

		return jwtService.generateToken(user);
	}

}
