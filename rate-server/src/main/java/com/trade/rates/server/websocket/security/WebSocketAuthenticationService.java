package com.trade.rates.server.websocket.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class WebSocketAuthenticationService {
	// This method MUST return a UsernamePasswordAuthenticationToken instance, the spring security chain is testing it with 'instanceof' later on. So don't use a subclass of it or any other class
	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String username, final String password) throws AuthenticationException {
		if (username == null || username.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
		}
		// Add logic for retrieving user/password from keycloak
		if (!"user".equals("user")) {
			throw new BadCredentialsException("Bad credentials for user " + username);
		}

		// null credentials, we do not pass the password along
		return new UsernamePasswordAuthenticationToken(
				username,
				null,
				Collections.singleton((GrantedAuthority) () -> "USER") // MUST provide at least one role
		);
	}
}