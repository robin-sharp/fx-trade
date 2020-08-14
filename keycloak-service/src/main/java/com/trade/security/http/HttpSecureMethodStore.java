package com.trade.security.http;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A Class that stores HttpSecureMethods.
 * <p>
 * HttpSecureMethods can be added individuals
 */

@Component
public class HttpSecureMethodStore {

	private Map<String, Set<HttpSecureMethod>> secureHttpMethods = new ConcurrentHashMap<String, Set<HttpSecureMethod>>();

	/**
	 * A webservice identifies a specific host:port, such as an oauth2 client.
	 */
	public Set<HttpSecureMethod> getHttpMethods(String webservice) {
		return secureHttpMethods.get(webservice);
	}

	public HttpSecureMethodStore addHttpSecureMethods(String webservice, Set<HttpSecureMethod> httpSecureMethods) {
		httpSecureMethods.forEach(hsp -> addHttpSecureMethod(webservice, hsp));
		return this;
	}

	public HttpSecureMethodStore addHttpSecureMethod(String webservice, String httpMethod, String httpPath, String... roles) {
		addHttpSecureMethod(webservice, new HttpSecureMethod(httpMethod, httpPath, new HashSet(Arrays.asList(roles))));
		return this;
	}

	public HttpSecureMethodStore addHttpSecureMethod(String webservice, String httpMethod, String httpPath, Set<String> roles) {
		addHttpSecureMethod(webservice, new HttpSecureMethod(httpMethod, httpPath, roles));
		return this;
	}

	public HttpSecureMethodStore addHttpSecureMethod(String webservice, HttpSecureMethod httpSecureMethod) {
		Set<HttpSecureMethod> httpMethods = secureHttpMethods.computeIfAbsent(webservice, k -> new HashSet<>());
		httpMethods.add(httpSecureMethod);
		return this;
	}
}
