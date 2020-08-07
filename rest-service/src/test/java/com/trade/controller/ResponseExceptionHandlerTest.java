package com.trade.controller;

import com.trade.util.TestObject;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.Validation;

import static com.trade.util.TestData.NULL_TEST_OBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseExceptionHandlerTest {

	@Test
	public void testHandleMethodArgumentNotValidException() throws Exception {
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(NULL_TEST_OBJECT, "testObject");
		adapter.validate(NULL_TEST_OBJECT, errors);
		MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
				new MethodParameter(ResponseExceptionHandlerTest.class.getDeclaredMethod("testMethod", TestObject.class), 0),
				errors);

		ResponseEntity responseEntity = new ResponseExceptionHandler().handleBadRequest(exception);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals(36, responseEntity.getHeaders().get(ResponseExceptionHandler.ERROR_ID).get(0).length());

		assertEquals("Validation failed for argument [0] in private void com.trade.controller.ResponseExceptionHandlerTest.testMethod(com.trade.util.TestObject) with 9 errors: [Field error in object 'testObject' on field 'dt': rejected value [null]; codes [NotNull.testObject.dt,NotNull.dt,NotNull.java.time.LocalDateTime,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.dt,dt]; arguments []; default message [dt]]; default message [dt is mandatory]] [Field error in object 'testObject' on field 'd': rejected value [null]; codes [NotNull.testObject.d,NotNull.d,NotNull.java.time.LocalDate,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.d,d]; arguments []; default message [d]]; default message [d is mandatory]] [Field error in object 'testObject' on field 'u': rejected value [null]; codes [NotNull.testObject.u,NotNull.u,NotNull.java.util.UUID,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.u,u]; arguments []; default message [u]]; default message [u is mandatory]] [Field error in object 'testObject' on field 's': rejected value [null]; codes [NotNull.testObject.s,NotNull.s,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.s,s]; arguments []; default message [s]]; default message [s is mandatory]] [Field error in object 'testObject' on field 'bd': rejected value [null]; codes [NotNull.testObject.bd,NotNull.bd,NotNull.java.math.BigDecimal,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.bd,bd]; arguments []; default message [bd]]; default message [bd is mandatory]] [Field error in object 'testObject' on field 'm': rejected value [null]; codes [NotNull.testObject.m,NotNull.m,NotNull.java.util.Map,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.m,m]; arguments []; default message [m]]; default message [m is mandatory]] [Field error in object 'testObject' on field 'l': rejected value [null]; codes [NotNull.testObject.l,NotNull.l,NotNull.java.util.List,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.l,l]; arguments []; default message [l]]; default message [l is mandatory]] [Field error in object 'testObject' on field 't': rejected value [null]; codes [NotNull.testObject.t,NotNull.t,NotNull.java.time.LocalTime,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.t,t]; arguments []; default message [t]]; default message [t is mandatory]] [Field error in object 'testObject' on field 'i': rejected value [null]; codes [NotNull.testObject.i,NotNull.i,NotNull.java.lang.Integer,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [testObject.i,i]; arguments []; default message [i]]; default message [i is mandatory]] ", responseEntity.getBody());
	}

	private void testMethod(TestObject testObject) {
	}

}