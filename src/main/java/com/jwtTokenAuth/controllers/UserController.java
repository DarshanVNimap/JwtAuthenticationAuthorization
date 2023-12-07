package com.jwtTokenAuth.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@GetMapping
	public String get() {
		return "user :: get";
	}
	
	@PostMapping
	public String post() {
		return "user :: post";
	}
	
	@PutMapping
	public String put() {
		return "user :: put";
	}
	
	@DeleteMapping
	public String delete() {
		return "user :: delete";
	}

}
