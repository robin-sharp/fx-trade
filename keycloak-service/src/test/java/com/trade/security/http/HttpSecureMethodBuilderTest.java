package com.trade.security.http;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.security.SecurityTestData.HTTP_SECURE_METHODS;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag(UNIT_TEST)
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
		String path = builder.getAnnotationPath(getMethod(ExampleTestController.class, "save"));
		assertEquals("/java/string", path);
	}

	@Test
	void testGetCodePath() {
		String path = builder.getAnnotationPath(getMethod(ExampleTestController.class, "findByCode"));
		assertEquals("/java/string/code/{code}", path);
	}

	@Test
	void testGetHttpMethod() {
		assertEquals("POST", builder.getHttpMethod(getMethod(ExampleTestController.class, "save")));
		assertEquals("GET", builder.getHttpMethod(getMethod(ExampleTestController.class, "findAll")));
		assertEquals("GET", builder.getHttpMethod(getMethod(ExampleTestController.class, "findByCode")));
		assertEquals("DELETE", builder.getHttpMethod(getMethod(ExampleTestController.class, "delete")));
	}

	@Test
	void testFindRoles() {
		Set<String> securedRoles = new LinkedHashSet(Arrays.asList(
				"String-Internal-Find-Role", "String-Support-Find-Role", "String-External-Find-Role"));
		assertEquals(securedRoles.toString(),
				builder.getRoles(String.class, getMethod(ExampleTestController.class, "findAll")).toString());
	}

	@Test
	void testFindByCodeRoles() {
		Set<String> securedRoles = new LinkedHashSet(Arrays.asList(
				"String-Internal-Find-Role", "String-Support-Find-Role", "String-External-Find-Role"));
		assertEquals(securedRoles.toString(),
				builder.getRoles(String.class, getMethod(ExampleTestController.class, "findByCode")).toString());
	}

	@Test
	void testSaveRoles() {
		Set<String> securedRoles = new LinkedHashSet(Arrays.asList(
				"String-Internal-Save-Role", "String-Support-Save-Role", "String-External-Save-Role"));
		assertEquals(securedRoles.toString(),
				builder.getRoles(String.class, getMethod(ExampleTestController.class, "saveAll")).toString());
	}

	@Test
	void testDeleteRoles() {
		Set<String> securedRoles = new LinkedHashSet(Arrays.asList("String-Support-Delete-Role"));
		assertEquals(securedRoles.toString(),
				builder.getRoles(String.class, getMethod(ExampleTestController.class, "delete")).toString());
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
			throw new RuntimeException(format("Failed to get method %s#%s", clazz.getSimpleName(), methodName), e);
		}
	}
}