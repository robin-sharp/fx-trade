package com.trade.security.service;

import org.junit.jupiter.api.Test;

import static com.trade.security.SecurityTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecureMethodStoreTest {

	@Test
	public void testAddSecureMethod() {

		SecureMethod SECURE_SERVICE_METHOD_1 = new SecureMethod(getClass(), "testAddSecureMethod", ROLES);

		SecureMethodStore secureMethodStore = new SecureMethodStore();
		secureMethodStore.addSecureMethod(SECURE_METHOD);
		secureMethodStore.addSecureMethod(SECURE_SERVICE_METHOD_1);

		assertEquals(SECURE_METHOD, secureMethodStore.getSecureMethod(SECURE_CLAZZ.getName(), SECURE_METHOD_NAME).get());
		assertEquals(SECURE_SERVICE_METHOD_1, secureMethodStore.getSecureMethod(getClass().getName(), "testAddSecureMethod").get());
	}
}
