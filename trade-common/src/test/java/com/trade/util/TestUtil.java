package com.trade.util;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {

	/**
	 * Offical Junit5 solution
	 */
	public static void assertEqual(BigDecimal bd1, BigDecimal bd2) {
		MatcherAssert.assertThat(bd1, Matchers.comparesEqualTo(bd2));
	}

	public static void assertJsonEquals(Collection<?> list1, Collection<?> list2) {
		assertEquals(list1.size(), list2.size());
		List<String> jsonList1 = list1.stream().map(o -> JsonUtil.toJson(o)).collect(Collectors.toList());
		List<String> jsonList2 = list2.stream().map(o -> JsonUtil.toJson(o)).collect(Collectors.toList());

		jsonList1.forEach(j -> jsonList2.remove(j));
		assertEquals(0, jsonList2.size(), "Not equal " + jsonList2);
	}

	public static void assertContains(Collection<?> collection, Object object) {
		assertTrue(collection.stream().filter(o -> o.equals(object)).findFirst().isPresent(),
				"object=" + object + " not present in" + collection);
	}

	/**
	 * Validate a Bean
	 */
	public static BeanPropertyBindingResult getValidationResult(Object object) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
		final BeanPropertyBindingResult errors = new BeanPropertyBindingResult(object, object.getClass().getSimpleName());
		adapter.validate(object, errors);
		return errors;
	}

	public static void assertValid(Object object) {
		BeanPropertyBindingResult result = getValidationResult(object);
		if (result.getErrorCount() > 0) {
			StringBuilder sb = new StringBuilder();
			result.getAllErrors().forEach(e -> sb.append("\n").append(e.toString()));
			assertEquals(0, result.getErrorCount(), sb.toString());
		}
	}

	/**
	 * Test Object isSerializable
	 */
	public static Object assertSerializable(Object input) {
		try {
			byte[] inputBytes = serialize(input);
			Object output = deserialize(inputBytes);
			byte[] outputBytes = serialize(output);
			Assertions.assertArrayEquals(inputBytes, outputBytes);
			return output;
		} catch (Exception e) {
			throw new IllegalArgumentException(format("Object {} is not serializable", input), e);
		}
	}

	public static <T> T assertJson(Object input, Class<T> clazz) {
		String inputJson = JsonUtil.toJson(input);
		T output = JsonUtil.fromJson(inputJson, clazz);
		String outputJson = JsonUtil.toJson(input);

		assertEquals(clazz, output.getClass());
		assertEquals(inputJson, outputJson);
		assertEquals(input, output);
		return output;
	}

	static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}

	static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}
}
