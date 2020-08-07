package com.trade.util;

import org.junit.jupiter.api.Test;

import static com.trade.util.JsonUtil.*;
import static com.trade.util.TestData.TEST_OBJECT;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

	@Test
	void testToAndFromJson() {
		assertEquals(TEST_OBJECT, fromJson(toJson(TEST_OBJECT), TestObject.class));
	}

	@Test
	void testToAndFromMap() {
		assertEquals(TEST_OBJECT, fromMap(toMap(toJson(TEST_OBJECT)), TestObject.class));
	}

	@Test
	public void testNull() {
		assertNull(toJson(null));
		assertNull(fromJson(null, String.class));
		assertNull(toMap(null));
		assertNull(fromMap(null, String.class));
	}

	@Test
	public void testToJsonThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			toJson(this);
		});
		assertEquals("Failed to write JsonUtilTest", e.getMessage());
	}

	@Test
	public void testFromJsonThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fromJson("x", getClass());
		});
		assertEquals("Failed to read x", e.getMessage());
	}

	@Test
	public void testNullClassFromJsonThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fromJson("hello world", null);
		});
		assertEquals("Failed to read hello world because no class set", e.getMessage());
	}

	@Test
	public void testToMapThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			toMap("[]");
		});
		assertEquals("Failed to read []", e.getMessage());
	}

	@Test
	public void testFromMapThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fromMap(singletonMap("hello", "world"), getClass());
		});
		assertEquals("Failed to read {hello=world}", e.getMessage());
	}

	@Test
	public void testNullClassFromMapThrowsException() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fromMap(singletonMap("hello", "world"), null);
		});
		assertEquals("Failed to read map {hello=world} because no class set", e.getMessage());
	}
}