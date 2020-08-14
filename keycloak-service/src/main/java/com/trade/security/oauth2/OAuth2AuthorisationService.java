package com.trade.security.oauth2;

import com.trade.security.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

/**
 * Keycloak OAuth2AuthorisationService used to check roles defined in the HttpSecureMethod store
 * for the endpoint being called are defined in the HttpRequest JWT bearer token.
 */
@Slf4j
public class OAuth2AuthorisationService implements HttpAuthorisationService {

	@Autowired
	private HttpSecureMethodStore secureMethodStore;

	@Autowired
	private HttpSecurityContext securityContext;

	@Override
	public void authorise(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		final String httpMethod = getHttpMethod(request);
		final String requestUri = getRequestURI(request);

		if ("/error".equals(requestUri)) {
			return;
		}

		final HttpSecurePrincipal httpSecurePrincipal = getHttpPrincipal();
		log.debug("httpSecurePrincipal={}", httpSecurePrincipal);

		final Set<HttpSecureMethod> httpMethods = getSecuredMethods(httpSecurePrincipal);
		if (httpMethods == null) {
			log.debug("Method is not secure httpMethod={} requestUri={}", httpMethod, requestUri);
			return;
		}

		log.debug("httpMethod={} requestUri={}", httpMethod, requestUri);

		final Optional<HttpSecureMethod> secureMethod = getSecureMethod(request, httpMethods);

		if (!secureMethod.isPresent()) {
			throw new SessionAuthenticationException(format("Failed to %s %s because required method is not secured",
					httpMethod, requestUri));
		}

		if (!secureMethod.get().hasRole(httpSecurePrincipal.getRoles())) {
			throw new SessionAuthenticationException(format("Failed to %s %s because users roles %s are not on securedMethod %s",
					httpMethod, requestUri, httpSecurePrincipal.getRoles(), secureMethod.get()));
		}
	}

	HttpSecurePrincipal getHttpPrincipal() {
		return securityContext.getHttpPrincipal();
	}

	String getHttpMethod(HttpServletRequest request) {
		return request.getMethod().toUpperCase();
	}

	String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	Set<HttpSecureMethod> getSecuredMethods(HttpSecurePrincipal httpSecurePrincipal) {
		return secureMethodStore.getHttpMethods(httpSecurePrincipal.getWebservice());
	}

	Optional<HttpSecureMethod> getSecureMethod(HttpServletRequest request, Set<HttpSecureMethod> httpMethods) {
		if (httpMethods == null) {
			return Optional.empty();
		}
		return httpMethods.stream().filter(hsm -> hsm.isMethod(getHttpMethod(request), getRequestURI(request))).findFirst();
	}
}