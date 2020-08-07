package com.trade.security.http;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a web endpoint and the roles required to be authorised.
 */
@Slf4j
@NoArgsConstructor
public class HttpSecureMethod {

	private String httpMethod;
	private String httpPath;
	private Pattern pathPattern;
	private Set<String> roles = new LinkedHashSet();

	public HttpSecureMethod(String httpMethod, String httpPath, Set<String> roles) {
		this.httpMethod = httpMethod.toUpperCase();
		this.httpPath = httpPath;
		pathPattern = getPathPattern(httpPath);
		this.roles.addAll(roles);
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getHttpPath() {
		return httpPath;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public boolean isAuthorised(String httpMethod, String httpPath, Set<String> clientRoles) {
		return hasHttpMethod(httpMethod) &&
				hasHttpPath(httpPath) &&
				hasRole(clientRoles);
	}

	public boolean hasRole(Set<String> clientRoles) {
		return roles.stream().filter(r -> clientRoles.contains(r)).findFirst().isPresent();
	}

	public boolean hasHttpMethod(String httpMethod) {
		return this.httpMethod.equals(httpMethod.toUpperCase());
	}

	public boolean hasHttpPath(String httpPath) {
		if (this.httpPath.equalsIgnoreCase(httpPath)) {
			return true;
		}
		if (pathPattern != null) {
			Matcher pathMatcher = pathPattern.matcher(httpPath);
			return pathMatcher.matches();
		}

		return false;
	}

	public boolean equals(Object object) {
		if (object instanceof HttpSecureMethod) {
			return ((HttpSecureMethod) object).httpMethod.equalsIgnoreCase(httpMethod) &&
					((HttpSecureMethod) object).httpPath.equalsIgnoreCase(httpPath);
		}
		return false;
	}

	Pattern getPathPattern(String httpPath) {
		if (httpPath.indexOf("{") > 0 && httpPath.indexOf("}") > 0) {
			String regExPath = httpPath.replaceAll("\\{.*?\\}", "[^/]+");
			log.debug("Compiling path {} {}", httpPath, regExPath);
			return Pattern.compile(regExPath, Pattern.CASE_INSENSITIVE);
		}

		return null;
	}

	public int hashcode() {
		return Objects.hash(httpMethod, httpPath);
	}

	public String toString() {
		return new StringBuffer().
				append("httpMethod=").append(httpMethod).
				append(", httpPath=").append(httpPath).
				append(", roles=").append(roles).
				toString();
	}
}
