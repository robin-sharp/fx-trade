package com.trade.security.http;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Tag(UNIT_TEST)
class HttpSecurePrincipalTest {


	@Test
	public void testConstructor() {
		HttpSecurePrincipal httpSecurePrincipal = HTTP_PRINCIPAL;
		assertEquals(WEBSERVICE, httpSecurePrincipal.getWebservice());
		assertEquals(PRINCIPAL_NAME, httpSecurePrincipal.getPrincipalName());
		assertEquals(ROLES, httpSecurePrincipal.getRoles());
	}

	@Test
	public void testEquals() {
		HttpSecurePrincipal httpSecurePrincipal = new HttpSecurePrincipal(
				HTTP_PRINCIPAL.getWebservice(),
				HTTP_PRINCIPAL.getPrincipalName(),
				GET_ROLES);
		assertEquals(HTTP_PRINCIPAL, httpSecurePrincipal);
	}

	@Test
	public void testNotEquals() {
		HttpSecurePrincipal httpSecurePrincipal = new HttpSecurePrincipal("", "", HTTP_PRINCIPAL.getRoles());
		assertNotEquals(HTTP_PRINCIPAL, httpSecurePrincipal);
	}

	@Test
	public void testHashcode() {
		assertEquals(-1236717178, HTTP_PRINCIPAL.hashCode());
	}

	@Test
	public void testToString() {
		assertEquals("HttpSecurePrincipal{webService=Webservice, principalName=robin.sharp, roles=[a, b]}", HTTP_PRINCIPAL.toString());
	}
}