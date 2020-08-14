package com.trade.controller;

import com.trade.util.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Validation;
import java.util.UUID;

import static com.trade.controller.ResponseExceptionHandler.ERROR_MESSAGE;
import static com.trade.util.TestData.NULL_TEST_OBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseExceptionHandlerTest {

	private ResponseExceptionHandler handler = new ResponseExceptionHandler();

	@Test
	public void testHandleGeneralExceptions() {
		checkResponseEntity(HttpStatus.FORBIDDEN, handler.handleAuthenticationException(new LockedException("Account locked")));
		checkResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, handler.handleRuntimeException(new RuntimeException("Too hot to handle")));
	}

	@Test
	public void testHandleMethodArgumentNotValidException() throws Exception {
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(NULL_TEST_OBJECT, "testObject");
		adapter.validate(NULL_TEST_OBJECT, errors);
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				new MethodParameter(ResponseExceptionHandlerTest.class.getDeclaredMethod("testMethod", TestObject.class), 0),
				errors);

		checkResponseEntity(HttpStatus.BAD_REQUEST, handler.handleMethodArgumentNotValid(exception));
	}

	//Used for testHandleMethodArgumentNotValidException
	private void testMethod(TestObject testObject) {
	}

	private void checkResponseEntity(HttpStatus httpStatus, ResponseEntity responseEntity) {
		assertEquals(httpStatus, responseEntity.getStatusCode());
		UUID.fromString(responseEntity.getHeaders().get(ResponseExceptionHandler.ERROR_ID).get(0));
		assertEquals(ERROR_MESSAGE, responseEntity.getBody());
	}
}