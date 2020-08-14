package com.trade.security.service;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.trade.security.SecurityTestData.*;
import static java.util.Collections.EMPTY_SET;
import static org.junit.jupiter.api.Assertions.*;

class SecureMethodTest {

	@Test
	public void testConstructor() {
		assertEquals(SECURE_CLAZZ, SECURE_METHOD.getSecureClass());
		assertEquals(SECURE_CLAZZ.getName(), SECURE_METHOD.getClassName());
		assertEquals(SECURE_METHOD_NAME, SECURE_METHOD.getMethodName());
		assertEquals(ROLES, SECURE_METHOD.getRoles());
	}

	@Test
	public void testConstructorArray() {
		SecureMethod SECURE_SERVICE_METHOD_1 = new SecureMethod(SECURE_CLAZZ, SECURE_METHOD_NAME, ROLE_A, ROLE_B);

		assertEquals(SECURE_METHOD.getRoles(), SECURE_SERVICE_METHOD_1.getRoles());
	}

	@Test
	public void testAuthorisation() {
		assertTrue(SECURE_METHOD.isAuthorised(SECURE_CLAZZ, SECURE_METHOD_NAME, ROLES));

		assertFalse(SECURE_METHOD.isAuthorised(getClass(), SECURE_METHOD_NAME, ROLES));
		assertFalse(SECURE_METHOD.isAuthorised(SECURE_CLAZZ, "blah", ROLES));
		assertFalse(SECURE_METHOD.isAuthorised(SECURE_CLAZZ, SECURE_METHOD_NAME, null));
		assertFalse(SECURE_METHOD.isAuthorised(SECURE_CLAZZ, SECURE_METHOD_NAME, EMPTY_SET));
		assertFalse(SECURE_METHOD.isAuthorised(SECURE_CLAZZ, SECURE_METHOD_NAME, GET_ROLES));
	}

	@Test
	public void testHash() {
		assertEquals(Objects.hash(SECURE_METHOD.getSecureClass().getName(), SECURE_METHOD.getMethodName()),
				SECURE_METHOD.hashCode());
	}

	@Test
	public void testEquals() {
		SecureMethod SECURE_SERVICE_METHOD_1 = new SecureMethod(
				SECURE_CLAZZ, SECURE_METHOD_NAME, GET_ROLES);

		assertEquals(SECURE_METHOD, SECURE_METHOD);
		assertEquals(SECURE_METHOD, SECURE_SERVICE_METHOD_1);
		assertEquals(SECURE_SERVICE_METHOD_1, SECURE_METHOD);
	}

	@Test
	public void testNotEquals() {
		SecureMethod SECURE_SERVICE_METHOD_1 = new SecureMethod(getClass(), "testNotEquals", ROLES);

		assertNotEquals(SECURE_METHOD, SECURE_SERVICE_METHOD_1);
		assertNotEquals(SECURE_SERVICE_METHOD_1, SECURE_METHOD);
	}
}