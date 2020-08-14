package com.trade.security.service;

/**
 * Check to see if the current principal and location is authorised
 */
public interface SecureAuthorisationService {

	void authorise(String className, String methodName);

	/**
	 * Authorise by looking at stacktrace, experimental.
	 */
	void authorise(Object secureObject);
}
