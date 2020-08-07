package com.trade.security.http;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static com.trade.security.http.HttpSecurityTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class HttpSecureMethodTest {

	@Test
	public void testGetHttpMethod() {
		assertEquals("GET", HTTP_SECURE_METHOD.getHttpMethod());
	}

	@Test
	public void testGetHttpPath() {
		assertEquals("/path/path", HTTP_SECURE_METHOD.getHttpPath());
	}

	@Test
	public void testGetRoles() {
		assertEquals(new LinkedHashSet<>(Arrays.asList("a", "b")), HTTP_SECURE_METHOD.getRoles());
	}

	@Test
	public void testIsAuthorised() {
		assertTrue(HTTP_SECURE_METHOD.isAuthorised("GET", "/path/path", new LinkedHashSet<>(Arrays.asList("b", "c"))));
		assertTrue(HTTP_SECURE_METHOD.isAuthorised("get", "/path/PATH", new LinkedHashSet<>(Arrays.asList("b", "c"))));

		assertFalse(HTTP_SECURE_METHOD.isAuthorised("POST", "/path/path", new LinkedHashSet<>(Arrays.asList("b", "c"))));
		assertFalse(HTTP_SECURE_METHOD.isAuthorised("GET", "/path", new LinkedHashSet<>(Arrays.asList("b", "c"))));
		assertFalse(HTTP_SECURE_METHOD.isAuthorised("GET", "/path/path", new LinkedHashSet<>(Arrays.asList("c"))));
	}

	@Test
	public void testHasRole() {
		assertTrue(HTTP_SECURE_METHOD.hasRole(new LinkedHashSet<>(Arrays.asList("b", "c"))));
		assertFalse(HTTP_SECURE_METHOD.hasRole(new LinkedHashSet<>(Arrays.asList("c", "d"))));
	}

	@Test
	public void testHasHttpMethod() {
		assertTrue(HTTP_SECURE_METHOD.hasHttpMethod("GET"));
		assertFalse(HTTP_SECURE_METHOD.hasHttpMethod("POST"));
		assertTrue(HTTP_SECURE_METHOD.hasHttpMethod("get"));
		assertFalse(HTTP_SECURE_METHOD.hasHttpMethod("POST"));
	}

	@Test
	public void testHasHttpPath() {
		assertTrue(HTTP_SECURE_METHOD.hasHttpPath("/path/path"));
		assertTrue(HTTP_SECURE_METHOD.hasHttpPath("/PATH/path"));
		assertFalse(HTTP_SECURE_METHOD.hasHttpPath("/path"));
	}

	@Test
	public void testHasPathVariable() {
		assertTrue(HTTP_SECURE_VARIABLE_METHOD.hasHttpPath("/path/path/1234"));
		assertTrue(HTTP_SECURE_VARIABLE_METHOD.hasHttpPath("/path/PATH/1234"));
		assertFalse(HTTP_SECURE_VARIABLE_METHOD.hasHttpPath("/path/path/1234/path/1234"));
		assertFalse(HTTP_SECURE_VARIABLE_METHOD.hasHttpPath("/path/path/"));
		assertFalse(HTTP_SECURE_VARIABLE_METHOD.hasHttpPath("/path/path"));
	}


	@Test
	public void testHasPathVariables() {
		assertTrue(HTTP_SECURE_VARIABLES_METHOD.hasHttpPath("/path/path/1234/path/1234"));
		assertTrue(HTTP_SECURE_VARIABLES_METHOD.hasHttpPath("/PATH/path/1234/PATH/1234"));
		assertFalse(HTTP_SECURE_VARIABLES_METHOD.hasHttpPath("/path/path/1234"));
		assertFalse(HTTP_SECURE_VARIABLES_METHOD.hasHttpPath("/path/path/1234/path/"));
		assertFalse(HTTP_SECURE_VARIABLES_METHOD.hasHttpPath("/path/path/1234/path"));
	}

	@Test
	public void testEquals() {
		assertEquals(new HttpSecureMethod("GET", "/path/path", new LinkedHashSet<>()), HTTP_SECURE_METHOD);
		assertNotEquals(new HttpSecureMethod("POST", "/path/path", new LinkedHashSet<>()), HTTP_SECURE_METHOD);
		assertNotEquals(new HttpSecureMethod("GET", "/path", new LinkedHashSet<>()), HTTP_SECURE_METHOD);
	}

	@Test
	public void testHashcode() {
		assertEquals(-1066058805, HTTP_SECURE_METHOD.hashcode());
	}

	@Test
	public void testToString() {
		assertEquals("httpMethod=GET, httpPath=/path/path, roles=[a, b]", HTTP_SECURE_METHOD.toString());
	}
}