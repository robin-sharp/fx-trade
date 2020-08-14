package com.trade.security.service;

import com.trade.security.http.HttpSecurePrincipal;
import com.trade.security.http.HttpSecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.Optional;

import static java.lang.String.format;

@Slf4j
public class SecureMethodAuthorisationService implements SecureAuthorisationService {

	@Autowired
	private HttpSecurityContext httpSecurityContext;

	@Autowired
	private SecureMethodStore secureMethodStore;

	/**
	 * Call authorise(this) as the secureObject to find the method that calls this.
	 */
	public void authorise(Object secureObject) {
		final String[] classAndMethodName = getClassAndMethodName(secureObject);
		final String className = classAndMethodName[0];
		final String methodName = classAndMethodName[1];

		authorise(className, methodName);
	}

	public void authorise(String className, String methodName) {
		final HttpSecurePrincipal httpSecurePrincipal = httpSecurityContext.getHttpPrincipal();

		log.debug("Checking principalName={} isAuthorised for className={}, methodName={}",
				httpSecurePrincipal.getPrincipalName(), className, methodName);

		final Optional<SecureMethod> secureMethod = secureMethodStore.getSecureMethod(className, methodName);
		if (!secureMethod.isPresent()) {
			throw new SessionAuthenticationException(format("Failed to %s %s because required method is not secured",
					className, methodName));
		}

		if (!secureMethod.get().hasRole(httpSecurePrincipal.getRoles())) {
			throw new SessionAuthenticationException(format("Failed to %s %s because users roles %s are not on securedMethod %s",
					className, methodName, httpSecurePrincipal.getRoles(), secureMethod.get()));
		}
	}

	/**
	 * Look up from the bottom of the stack for the first method called by the secure object.
	 */
	private String[] getClassAndMethodName(Object secureObject) {
		final StackTraceElement[] st = Thread.currentThread().getStackTrace();
		for (int index = 3; index < st.length; index++) {
			final StackTraceElement ste = st[index];
			if (secureObject.getClass().getName().equals(ste.getClassName())) {
				return new String[]{ste.getClassName().substring(ste.getClassName().lastIndexOf('.') + 1), ste.getMethodName()};
			}
		}
		return new String[]{secureObject.getClass().getSimpleName(), ""};
	}
}
