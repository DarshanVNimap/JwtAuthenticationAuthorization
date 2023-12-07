package com.jwtTokenAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ProblemDetail badCradentialException(BadCredentialsException ex) {

		ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
		error.setProperty("Access Denied", "Authentication failure");

		return error;
	}

	@ExceptionHandler(SignatureException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ProblemDetail signatureInvalid(SignatureException ex) {
	    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
	    error.setProperty("Access Denied", "Invalid token signature");
	    return error;
	}

	@ExceptionHandler(MalformedJwtException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ProblemDetail malformedJwtException(MalformedJwtException ex) {
	    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
	    error.setProperty("Access Denied", "Invalid token");
	    return error;
	}
	

	@ExceptionHandler(ExpiredJwtException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ProblemDetail expiredJwtException(ExpiredJwtException  		ex) {
	    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
	    error.setProperty("Access Denied", "Invalid token");
	    return error;
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ProblemDetail exception(Exception ex) {
	    ProblemDetail error = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
	    error.setProperty("Access Denied", "Something went wrong!!");
	    return error;
	}

}
