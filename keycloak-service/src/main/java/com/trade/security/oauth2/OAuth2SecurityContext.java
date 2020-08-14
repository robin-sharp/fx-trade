package com.trade.security.oauth2;

import com.trade.security.http.HttpSecurePrincipal;
import com.trade.security.http.HttpSecurityContext;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Set;

/**
 * Session security informtaion from the Keycloak OAuth2 source
 */
public class OAuth2SecurityContext implements HttpSecurityContext {

	public HttpSecurePrincipal getHttpPrincipal() {
		KeycloakPrincipal keycloakPrinciple = getKeycloakPrincipal();
		return new HttpSecurePrincipal(
				getKeycloakWebService(keycloakPrinciple),
				getKeycloakPrincipalName(keycloakPrinciple),
				getKeycloakRoles(keycloakPrinciple));
	}

	KeycloakPrincipal getKeycloakPrincipal() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			final Object principal = authentication.getPrincipal();
			if (principal instanceof KeycloakPrincipal) {
				return (KeycloakPrincipal.class.cast(principal));
			}
		}

		return null;
	}

	String getKeycloakPrincipalName(KeycloakPrincipal keycloakPrincipal) {
		return keycloakPrincipal.getName();
	}

	String getKeycloakWebService(KeycloakPrincipal keycloakPrinciple) {
		return keycloakPrinciple.getKeycloakSecurityContext().getToken().getIssuedFor();
	}

	Set<String> getKeycloakRoles(KeycloakPrincipal keycloakPrinciple) {
		final Map<String, AccessToken.Access> resourceAccess = keycloakPrinciple.getKeycloakSecurityContext().getToken().getResourceAccess();
		final AccessToken.Access access = resourceAccess.get(getKeycloakWebService(keycloakPrinciple));

		return access != null ? access.getRoles() : null;
	}
}