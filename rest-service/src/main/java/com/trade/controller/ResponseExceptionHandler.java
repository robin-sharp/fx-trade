package com.trade.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

	final static String ERROR_ID = "errorId";
	final static String ERROR_MESSAGE = "Please search server logs for errorId";

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		return getResponseEntity(exception, HttpStatus.BAD_REQUEST);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
		return getResponseEntity(exception, HttpStatus.FORBIDDEN);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
		return getResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity getResponseEntity(Exception exception, HttpStatus httpStatus) {
		UUID errorId = UUID.randomUUID();
		log.error(format("Error errorId=%s message=%s", errorId, exception.getMessage()), exception);
		return ResponseEntity.
				status(httpStatus).
				header(ERROR_ID, errorId.toString()).
				body(ERROR_MESSAGE);
	}
}