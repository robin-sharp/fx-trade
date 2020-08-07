package com.trade.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TestData {

	public final static Object TEST_OBJECT = new TestObject(
			1, "Hello World", new BigDecimal("12345.67890"),
			LocalTime.now(), LocalDate.now(), LocalDateTime.now(),
			UUID.randomUUID(), new ArrayList(Arrays.asList("Hello", "World")), new HashMap(Collections.singletonMap("Hello", "World")));

	public final static Object NULL_TEST_OBJECT = new TestObject();
}
