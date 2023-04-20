package com.example.demo.globalExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExcepHandler {

	// invaild input from user exception handling
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}

	// custom exception handling
	@ExceptionHandler(CustomException.class)
	public String handleRequest(CustomException ex) {
		return ex.getMessage();
	}

	// wrongpassword exception handling
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(BadCredentialsException.class)
	public String handleInvalidpassword(BadCredentialsException ex) {
		return ex.getMessage();
	}

	// usernotfound exception handling
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(UsernameNotFoundException.class)
	public String handleInvalidpassword(UsernameNotFoundException ex) {
		return ex.getMessage();
	}

}
