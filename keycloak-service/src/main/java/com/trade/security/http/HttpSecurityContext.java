package com.trade.security.http;

/**
 * Class representing current session Http security information
 */
public interface HttpSecurityContext {
	HttpSecurePrincipal getHttpPrincipal();
}
