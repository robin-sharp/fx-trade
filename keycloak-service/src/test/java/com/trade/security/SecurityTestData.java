package com.trade.security;

import com.trade.security.http.HttpSecureMethod;
import com.trade.security.http.HttpSecurePrincipal;
import com.trade.security.service.SecureMethod;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SecurityTestData {

	public final static String WEBSERVICE = "Webservice";

	public final static String ROLE_A = "a";

	public final static String ROLE_B = "b";

	public final static Set<String> ROLES = new LinkedHashSet<>(Arrays.asList(ROLE_A, ROLE_B));

	public final static String PRINCIPAL_NAME = "robin.sharp";

	public final static HttpSecurePrincipal HTTP_PRINCIPAL = new HttpSecurePrincipal(WEBSERVICE, PRINCIPAL_NAME, ROLES);

	public final static String HTTP_GET_METHOD = "GET";

	public final static String HTTP_POST_METHOD = "POST";

	public final static String HTTP_DELETE_METHOD = "DELETE";

	public final static String URI_PATH = "/path/path";

	public final static HttpSecureMethod HTTP_SECURE_METHOD = new HttpSecureMethod("GET", URI_PATH, ROLES);
	public final static HttpSecureMethod HTTP_SECURE_VARIABLE_METHOD = new HttpSecureMethod("GET", "/path/path/{var1}", ROLES);
	public final static HttpSecureMethod HTTP_SECURE_VARIABLES_METHOD = new HttpSecureMethod("GET", "/path/path/{var1}/path/{var2}", ROLES);


	public final static Set<String> GET_ROLES = new LinkedHashSet<>(Arrays.asList("String-Internal-Find-Role", "String-Support-Find-Role", "String-External-Find-Role"));
	public final static Set<String> POST_ROLES = new LinkedHashSet<>(Arrays.asList("String-Internal-Save-Role", "String-Support-Save-Role", "String-External-Save-Role"));
	public final static Set<String> DELETE_ROLES = new LinkedHashSet<>(Arrays.asList("String-Support-Delete-Role"));

	public final static HttpSecureMethod GET_SINGLE_SECURE_METHOD = new HttpSecureMethod("GET", "/test/v1/controller/java/string", GET_ROLES);

	public final static Set<HttpSecureMethod> HTTP_SECURE_METHODS = new LinkedHashSet();
	public final static String SECURE_SERVICE_LEVEL_OPEN = "O";
	public final static String SECURE_SERVICE_LEVEL_RESTRICTED = "R";
	public final static String SECURE_SERVICE_LEVEL_CLOSED = "C";
	public final static Class SECURE_CLAZZ = SecurityTestData.class;
	public final static String SECURE_METHOD_NAME = "serviceMethod";
	public final static SecureMethod SECURE_METHOD = new SecureMethod(SECURE_CLAZZ, SECURE_METHOD_NAME, ROLES);
	public final static SecureMethod SECURE_GET_METHOD = new SecureMethod(SECURE_CLAZZ, SECURE_METHOD_NAME, GET_ROLES);
	public final static SecureMethod SECURE_SAVE_METHOD = new SecureMethod(SECURE_CLAZZ, SECURE_METHOD_NAME, POST_ROLES);
	public final static SecureMethod SECURE_DELETE_METHOD = new SecureMethod(SECURE_CLAZZ, SECURE_METHOD_NAME, DELETE_ROLES);

	static {
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("DELETE", "/test/v1/controller/java/string", DELETE_ROLES));
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("GET", "/test/v1/controller/java/strings", GET_ROLES));
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("GET", "/test/v1/controller/java/string/code/{code}", GET_ROLES));
		HTTP_SECURE_METHODS.add(GET_SINGLE_SECURE_METHOD);
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("POST", "/test/v1/controller/java/string", POST_ROLES));
		HTTP_SECURE_METHODS.add(new HttpSecureMethod("POST", "/test/v1/controller/java/strings", POST_ROLES));
	}

	public void serviceMethod() {
	}
}
