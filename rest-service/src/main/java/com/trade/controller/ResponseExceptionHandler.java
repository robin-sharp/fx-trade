package com.trade.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.UUID;

import static java.lang.String.format;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

	final static String ERROR_ID = "errorId";
	private final boolean RETURN_ERROR_MESSAGE = true;
	private final String ALT_ERROR_MESSAGE = " Please look at server logs for message";

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleBadRequest(MethodArgumentNotValidException exception) {
		UUID errorId = UUID.randomUUID();
		log.error(format("Error errorId=%s message=%s", errorId, exception.getMessage()), exception);
		return ResponseEntity.
				status(HttpStatus.BAD_REQUEST).
				header(ERROR_ID, errorId.toString()).
				body(RETURN_ERROR_MESSAGE ? exception.getMessage() : ALT_ERROR_MESSAGE);
	}
}
