package com.trade.security.http;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.*;
import static com.trade.util.TestUtil.assertContains;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(UNIT_TEST)
public class HttpSecureMethodStoreTest {

	@Test
	public void testAddHttpSecureMethods() {
		HttpSecureMethodStore secureMethodStore = new HttpSecureMethodStore();
		secureMethodStore.addHttpSecureMethods(WEBSERVICE, HTTP_SECURE_METHODS);
		assertEquals(HTTP_SECURE_METHODS, secureMethodStore.getHttpMethods(WEBSERVICE));
	}

	@Test
	public void testAddHttpSecureMethodRolesVarArg() {
		HttpSecureMethodStore secureMethodStore = new HttpSecureMethodStore();
		secureMethodStore.addHttpSecureMethod(WEBSERVICE, HTTP_SECURE_METHOD.getHttpMethod(), HTTP_SECURE_METHOD.getHttpPath(), ROLE_A, ROLE_B);
		secureMethodStore.addHttpSecureMethod(WEBSERVICE, HTTP_SECURE_VARIABLE_METHOD.getHttpMethod(), HTTP_SECURE_VARIABLE_METHOD.getHttpPath(), ROLE_A, ROLE_B);

		Set<HttpSecureMethod> secureMethods = secureMethodStore.getHttpMethods(WEBSERVICE);
		assertContains(secureMethods, HTTP_SECURE_METHOD);
		assertContains(secureMethods, HTTP_SECURE_VARIABLE_METHOD);
	}

	@Test
	public void testAddHttpSecureMethodRolesSet() {
		HttpSecureMethodStore secureMethodStore = new HttpSecureMethodStore();
		secureMethodStore.addHttpSecureMethod(WEBSERVICE, HTTP_SECURE_METHOD.getHttpMethod(), HTTP_SECURE_METHOD.getHttpPath(), ROLES);
		secureMethodStore.addHttpSecureMethod(WEBSERVICE, HTTP_SECURE_VARIABLE_METHOD.getHttpMethod(), HTTP_SECURE_VARIABLE_METHOD.getHttpPath(), ROLES);

		Set<HttpSecureMethod> secureMethods = secureMethodStore.getHttpMethods(WEBSERVICE);
		assertContains(secureMethods, HTTP_SECURE_METHOD);
		assertContains(secureMethods, HTTP_SECURE_VARIABLE_METHOD);
	}

//	public HttpSecureMethodStore addHttpSecureMethod(String webservice, String httpMethod, String httpPath, Set<String> roles) {
//		addHttpSecureMethod(webservice, new HttpSecureMethod(httpMethod, httpPath, roles));
//		//return this;
//	}
//
//	public HttpSecureMethodStore addHttpSecureMethod(String webservice, HttpSecureMethod httpSecureMethod) {
}
