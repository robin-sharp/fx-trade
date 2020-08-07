package com.trade.security.http;

import org.junit.jupiter.api.Test;

import static com.trade.security.http.HttpSecurityTestData.HTTP_SECURE_METHODS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpSecureMethodStoreTest {

	@Test
	public void testAddHttpSecureMethods() {
		HttpSecureMethodStore secureMethodStore = new HttpSecureMethodStore();
		secureMethodStore.addHttpSecureMethods("Webservice", HTTP_SECURE_METHODS);
		assertEquals(HTTP_SECURE_METHODS, secureMethodStore.getHttpMethods("Webservice"));
	}
}
