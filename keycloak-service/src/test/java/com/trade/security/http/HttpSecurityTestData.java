package com.trade.security.http;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class HttpSecurityTestData {

	public final static HttpSecureMethod HTTP_SECURE_METHOD = new HttpSecureMethod("GET", "/path/path", new LinkedHashSet<>(Arrays.asList("a", "b")));

	public final static HttpSecureMethod HTTP_SECURE_VARIABLE_METHOD = new HttpSecureMethod("GET", "/path/path/{var1}", new LinkedHashSet<>(Arrays.asList("a", "b")));

	public final static HttpSecureMethod HTTP_SECURE_VARIABLES_METHOD = new HttpSecureMethod("GET", "/path/path/{var1}/path/{var2}", new LinkedHashSet<>(Arrays.asList("a", "b")));

	public final static Set<HttpSecureMethod> HTTP_SECURE_METHODS = new LinkedHashSet();

	static {
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("DELETE", "/test/v1/controller/java/string",
				new LinkedHashSet(Arrays.asList("String-Internal-Delete-Role"))));

		HTTP_SECURE_METHODS.add(new HttpSecureMethod("GET", "/test/v1/controller/java/strings",
				new LinkedHashSet(Arrays.asList("String-Internal-Find-Role", "String-External-Find-Role"))));

		HTTP_SECURE_METHODS.add(new HttpSecureMethod("GET", "/test/v1/controller/java/string",
				new LinkedHashSet(Arrays.asList("String-Internal-Find-Role", "String-External-Find-Role"))));

		HTTP_SECURE_METHODS.add(new HttpSecureMethod("POST", "/test/v1/controller/java/string",
				new LinkedHashSet(Arrays.asList("String-Internal-Save-Role", "String-External-Save-Role"))));

		HTTP_SECURE_METHODS.add(new HttpSecureMethod("POST", "/test/v1/controller/java/strings",
				new LinkedHashSet(Arrays.asList("String-Internal-Save-Role", "String-External-Save-Role"))));
	}
}
