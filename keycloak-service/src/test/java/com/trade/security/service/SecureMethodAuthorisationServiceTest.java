package com.trade.security.service;

import com.trade.security.http.HttpSecurityContext;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.Optional;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
class SecureMethodAuthorisationServiceTest {

	private final static String CLASS_NAME = "SecureMethodAuthorisationServiceTest";

	@Mock
	private HttpSecurityContext httpSecurityContext;

	@Mock
	private SecureMethodStore secureMethodStore;

	@InjectMocks
	private SecureMethodAuthorisationService secureMethodAuthorisationService;

	@Test
	public void testAuthorise() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(HTTP_PRINCIPAL);
		when(secureMethodStore.getSecureMethod(CLASS_NAME, "testAuthorise")).thenReturn(Optional.of(SECURE_METHOD));

		secureMethodAuthorisationService.authorise(CLASS_NAME, "testAuthorise");
	}

	@Test
	public void testAuthoriseUsingStackTrace() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(HTTP_PRINCIPAL);
		when(secureMethodStore.getSecureMethod(CLASS_NAME, "testAuthoriseUsingStackTrace")).
				thenReturn(Optional.of(SECURE_METHOD));

		secureMethodAuthorisationService.authorise(this);
	}

	@Test
	public void testNotAuthorisedMethod() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(HTTP_PRINCIPAL);
		when(secureMethodStore.getSecureMethod(anyString(), anyString())).thenReturn(Optional.empty());

		assertThrows(SessionAuthenticationException.class, () -> {
			secureMethodAuthorisationService.authorise(CLASS_NAME, "blah");
		});
	}

	@Test
	public void testNotAuthorisedRoles() {
		when(httpSecurityContext.getHttpPrincipal()).thenReturn(HTTP_PRINCIPAL);
		when(secureMethodStore.getSecureMethod(CLASS_NAME, "testNotAuthorisedRoles")).
				thenReturn(Optional.of(SECURE_GET_METHOD));

		assertThrows(SessionAuthenticationException.class, () -> {
			secureMethodAuthorisationService.authorise(CLASS_NAME, "testNotAuthorisedRoles");
		});
	}
}