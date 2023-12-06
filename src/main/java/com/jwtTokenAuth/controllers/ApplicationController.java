package com.jwtTokenAuth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/u")
public class ApplicationController {

	@GetMapping("/user")
	public ResponseEntity<?> demo(){
		try {
		return ResponseEntity.ok("This is secure!! and access by both");
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("it is an error" , HttpStatus.BAD_GATEWAY);
		}
	}
	@GetMapping("/a")
	public ResponseEntity<?> adminUrl(){
		return ResponseEntity.ok("This is secure!! and access by admin");
	}
	
}
