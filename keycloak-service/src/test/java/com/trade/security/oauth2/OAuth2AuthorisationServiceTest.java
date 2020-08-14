package com.trade.security.oauth2;

import com.trade.security.http.HttpSecureMethod;
import com.trade.security.http.HttpSecurePrincipal;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag(UNIT_TEST)
public class OAuth2AuthorisationServiceTest {


	@Test
	public void testAuthorise() throws ServletException {
		OAuth2AuthorisationService securityService = new TestAuthorisationService();
		securityService.authorise(null, null);
	}

	@Test
	public void testInsecureHttpMethod() throws ServletException {
		OAuth2AuthorisationService securityService = new TestAuthorisationService() {
			@Override
			String getHttpMethod(HttpServletRequest request) {
				return "INSECURE";
			}
		};

		securityService.authorise(null, null);
		;
	}

	@Test
	public void testNullRoles() throws ServletException {
		OAuth2AuthorisationService securityService = new TestAuthorisationService() {
			@Override
			HttpSecurePrincipal getHttpPrincipal() {
				return new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, null);
			}
		};

		assertThrows(SessionAuthenticationException.class, () -> {
			securityService.authorise(null, null);
		});
	}

	@Test
	public void testNoRoles() throws ServletException {
		OAuth2AuthorisationService securityService = new TestAuthorisationService() {
			@Override
			HttpSecurePrincipal getHttpPrincipal() {
				return new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, Collections.EMPTY_SET);
			}
		};

		assertThrows(SessionAuthenticationException.class, () -> {
			securityService.authorise(null, null);
		});
	}

	@Test
	public void testWrongRoles() throws ServletException {
		OAuth2AuthorisationService securityService = new TestAuthorisationService() {
			@Override
			HttpSecurePrincipal getHttpPrincipal() {
				return new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, ROLES);
			}
		};

		assertThrows(SessionAuthenticationException.class, () -> {
			securityService.authorise(null, null);
		});
	}

	class TestAuthorisationService extends OAuth2AuthorisationService {
		@Override
		String getHttpMethod(HttpServletRequest request) {
			return HTTP_GET_METHOD;
		}

		@Override
		String getRequestURI(HttpServletRequest request) {
			return URI_PATH;
		}

		@Override
		HttpSecurePrincipal getHttpPrincipal() {
			return new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, GET_ROLES);
		}

		@Override
		Set<HttpSecureMethod> getSecuredMethods(HttpSecurePrincipal httpSecurePrincipal) {
			return HTTP_SECURE_METHODS;
		}

		@Override
		Optional<HttpSecureMethod> getSecureMethod(HttpServletRequest request, Set<HttpSecureMethod> httpMethods) {
			return Optional.of(GET_SINGLE_SECURE_METHOD);
		}
	}
}
