package com.trade.security.http;

import java.util.Objects;
import java.util.Set;

/**
 * Class Representing a Secure Http Principal
 */
public final class HttpSecurePrincipal {

	private final String webService;
	private final String principalName;
	private final Set<String> roles;

	public HttpSecurePrincipal(String webService, String principalName, Set<String> roles) {
		this.webService = webService;
		this.principalName = principalName;
		this.roles = roles;
	}

	public String getWebservice() {
		return webService;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HttpSecurePrincipal that = (HttpSecurePrincipal) o;
		return webService.equals(that.webService) &&
				principalName.equals(that.principalName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(webService, principalName);
	}

	@Override
	public String toString() {
		return new StringBuilder("HttpSecurePrincipal{").
				append("webService=").append(webService).
				append(", principalName=").append(principalName).
				append(", roles=").append(roles).
				append("}").toString();
	}
}
