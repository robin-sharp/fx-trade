package com.trade.security.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.lang.String.format;

/**
 * A Class that automatically generates HttpSecureMethods from Spring Annotations on a Controller.
 */
@Slf4j
public class HttpSecureMethodBuilder {

	public Set<HttpSecureMethod> buildHttpSecureMethods(Class controllerClass, Class resourceClass) {
		try {
			log.debug("Building secureMethods for controller={} resource={}", controllerClass.getSimpleName(), resourceClass.getSimpleName());
			final Set<HttpSecureMethod> secureMethods = new LinkedHashSet();

			if (!isRestController(controllerClass)) {
				throw new IllegalArgumentException(format("Failed to addSecureMethods %s is not a RestController", controllerClass.getName()));
			}

			final String requestPath = getRequestPath(controllerClass);

			final Method[] methods = controllerClass.getDeclaredMethods();
			Map<String, Method> sortedMethods = new TreeMap();
			for (int index = 0; index < methods.length; index++) {
				if (!Modifier.isPublic(methods[index].getModifiers())) {
					continue;
				}
				sortedMethods.put(methods[index].getName(), methods[index]);
			}

			sortedMethods.values().forEach(method -> {
				secureMethods.add(new HttpSecureMethod(
						getHttpMethod(method), requestPath + getPath(method), getRoles(resourceClass, method)));
			});

			log.debug("Built secureMethods={}", secureMethods);

			return secureMethods;
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to addSecureMethods to %s", controllerClass.getName()));
		}
	}

	boolean isRestController(Class controllerClass) {
		return controllerClass.getAnnotation(RestController.class) != null;
	}

	String getRequestPath(Class controllerClass) {
		final RequestMapping requestMappingAnnotation = (RequestMapping) controllerClass.getAnnotation(RequestMapping.class);
		return requestMappingAnnotation != null ? requestMappingAnnotation.value()[0] : "";
	}

	String getPath(Method method) {
		if (method.getAnnotation(GetMapping.class) != null) {
			return method.getAnnotation(GetMapping.class).value()[0];
		}
		if (method.getAnnotation(PostMapping.class) != null) {
			return method.getAnnotation(PostMapping.class).value()[0];
		}
		if (method.getAnnotation(PutMapping.class) != null) {
			return method.getAnnotation(PutMapping.class).value()[0];
		}
		if (method.getAnnotation(DeleteMapping.class) != null) {
			return method.getAnnotation(DeleteMapping.class).value()[0];
		}

		return null;
	}

	String getHttpMethod(Method method) {
		if (method.getAnnotation(GetMapping.class) != null) {
			return "GET";
		}
		if (method.getAnnotation(PostMapping.class) != null) {
			return "POST";
		}
		if (method.getAnnotation(PutMapping.class) != null) {
			return "PUT";
		}
		if (method.getAnnotation(DeleteMapping.class) != null) {
			return "DELETE";
		}

		return null;
	}

	Set<String> getRoles(Class resourceClass, Method method) {
		final Set<String> roles = new LinkedHashSet();

		String httpMethod = getHttpMethod(method);
		if ("GET".equals(httpMethod)) {
			roles.add(resourceClass.getSimpleName() + "-Internal-Find-Role");
			roles.add(resourceClass.getSimpleName() + "-External-Find-Role");
		}
		if ("POST".equals(httpMethod)) {
			roles.add(resourceClass.getSimpleName() + "-Internal-Save-Role");
			roles.add(resourceClass.getSimpleName() + "-External-Save-Role");
		}
		if ("DELETE".equals(httpMethod)) {
			roles.add(resourceClass.getSimpleName() + "-Internal-Delete-Role");
		}

		return roles;
	}
}
