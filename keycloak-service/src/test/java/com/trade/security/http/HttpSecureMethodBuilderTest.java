package com.trade.security.http;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.trade.security.http.HttpSecurityTestData.HTTP_SECURE_METHODS;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpSecureMethodBuilderTest {

	private HttpSecureMethodBuilder builder = new HttpSecureMethodBuilder();

	@Test
	void testBuildSecureMethods() {
		assertEquals(HTTP_SECURE_METHODS.toString(), builder.buildHttpSecureMethods(ExampleTestController.class, String.class).toString());
	}

	@Test
	void testIsController() {
		assertTrue(builder.isRestController(ExampleTestController.class));
	}

	@Test
	void testGetRequestPath() {
		String requestPath = builder.getRequestPath(ExampleTestController.class);
		assertEquals("/test/v1/controller", requestPath);
	}

	@Test
	void testGetPath() {
		String path = builder.getPath(getMethod(ExampleTestController.class, "save"));
		assertEquals("/java/string", path);
	}

	@Test
	void testGetHttpMethod() {
		assertEquals("POST", builder.getHttpMethod(getMethod(ExampleTestController.class, "save")));
		assertEquals("GET", builder.getHttpMethod(getMethod(ExampleTestController.class, "findAll")));
		assertEquals("DELETE", builder.getHttpMethod(getMethod(ExampleTestController.class, "delete")));
	}

	@Test
	void testGetRoles() {
		Set<String> securedRoles = new LinkedHashSet(Arrays.asList("String-Internal-Save-Role", "String-External-Save-Role"));
		assertEquals(securedRoles.toString(),
				builder.getRoles(String.class, getMethod(ExampleTestController.class, "saveAll")).toString());
	}

	private Method getMethod(Class clazz, String methodName) {
		try {
			Method[] methods = clazz.getDeclaredMethods();
			for (int index = 0; index < methods.length; index++) {
				if (methods[index].getName().equals(methodName)) {
					return methods[index];
				}
			}
			throw new RuntimeException();
		} catch (Exception e) {
			throw new RuntimeException(format("Failed to get method %s#%s", clazz.getSimpleName(), methodName));
		}
	}
}