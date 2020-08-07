package com.trade.util;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;

import static com.trade.util.TestData.NULL_TEST_OBJECT;
import static com.trade.util.TestData.TEST_OBJECT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationTest {

	@Test
	public void testTestObjectIsValid() {
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(TEST_OBJECT, "testObject");
		adapter.validate(TEST_OBJECT, errors);
		assertEquals(0, errors.getErrorCount());
	}

	@Test
	public void testNullTestObjectIsInvalid() {
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());

		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(NULL_TEST_OBJECT, "testObject");
		adapter.validate(NULL_TEST_OBJECT, errors);
		assertEquals(9, errors.getErrorCount());
	}
}
