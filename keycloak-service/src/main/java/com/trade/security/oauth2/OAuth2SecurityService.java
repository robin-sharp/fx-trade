package com.trade.security.oauth2;

import com.trade.security.HttpAuthorisationService;
import com.trade.security.http.HttpSecureMethod;
import com.trade.security.http.HttpSecureMethodStore;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

/**
 * Keycloak OAuth2SecurityService used to check roles defined in the HttpSecureMethod store
 * for the endpoint being called are defined in the HttpRequest JWT bearer token.
 */
@Slf4j
public class OAuth2SecurityService implements HttpAuthorisationService {

	@Autowired
	private HttpSecureMethodStore secureMethodStore;

	@Override
	public void authorise(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final KeycloakPrincipal keycloakPrinciple = getKeycloakPrincipal(authentication);
		final KeycloakSecurityContext keycloakSecurityContext = keycloakPrinciple.getKeycloakSecurityContext();
		final String webservice = keycloakSecurityContext.getToken().getIssuedFor();
		final Map<String, AccessToken.Access> resourceAccess = keycloakSecurityContext.getToken().getResourceAccess();
		final Set<String> clientRoles = resourceAccess.get(webservice).getRoles();

		log.debug("realm={} principal={} roles={}", webservice, keycloakPrinciple.getName(), clientRoles);

		authorise(request, response, authentication, keycloakPrinciple, webservice, clientRoles);
	}

	protected void authorise(HttpServletRequest request, HttpServletResponse response,
							 Authentication authentication, Principal principal,
							 String webservice, Set<String> clientRoles) {

		final Set<HttpSecureMethod> httpMethods = secureMethodStore.getHttpMethods(webservice);
		if (httpMethods == null) {
			return;
		}
		final String httpMethod = request.getMethod().toUpperCase();
		final String requestUri = request.getRequestURI();

		httpMethods.stream().filter(hsm -> hsm.
				isAuthorised(httpMethod, requestUri, clientRoles)).
				findFirst().
				orElseThrow(() -> new AuthenticationException(format("Failed to %s %s because no authorised roles %s", httpMethod, requestUri, clientRoles)) {
				});
	}

	private KeycloakPrincipal getKeycloakPrincipal(Authentication authentication) {
		if (authentication != null) {
			final Object principal = authentication.getPrincipal();
			if (principal instanceof KeycloakPrincipal) {
				return (KeycloakPrincipal.class.cast(principal));
			}
		}

		return null;
	}

}
