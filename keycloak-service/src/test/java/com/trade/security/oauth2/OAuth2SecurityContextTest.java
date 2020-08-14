package com.trade.security.oauth2;

import com.trade.security.http.HttpSecurePrincipal;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;

import java.util.Set;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag(UNIT_TEST)
public class OAuth2SecurityContextTest {

	@Test
	public void testConstructor() {
		HttpSecurePrincipal httpSecurePrincipal = new TestSecurityContext().getHttpPrincipal();
		assertEquals(PRINCIPAL_NAME, httpSecurePrincipal.getPrincipalName());
		assertEquals(WEBSERVICE, httpSecurePrincipal.getWebservice());
		assertEquals(ROLES, httpSecurePrincipal.getRoles());
	}

	class TestSecurityContext extends OAuth2SecurityContext {
		@Override
		String getKeycloakPrincipalName(KeycloakPrincipal k) {
			return PRINCIPAL_NAME;
		}

		;

		@Override
		String getKeycloakWebService(KeycloakPrincipal k) {
			return WEBSERVICE;
		}

		@Override
		Set<String> getKeycloakRoles(KeycloakPrincipal k) {
			return ROLES;
		}
	}
}
