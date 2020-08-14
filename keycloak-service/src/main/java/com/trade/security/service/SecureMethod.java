package com.trade.security.service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Class representing a secure Java method, requiring roles for it to be authorised.
 */
public class SecureMethod {

	private final Class secureClass;
	private final String methodName;
	private final Set<String> roles;

	public SecureMethod(Class secureClass, String methodName, Object... roles) {
		this(secureClass, methodName,
				new LinkedHashSet(Arrays.asList(roles).stream().map(Object::toString).collect(Collectors.toList())));
	}

	public SecureMethod(Class secureClass, String methodName, Set<String> roles) {
		this.secureClass = secureClass;
		this.methodName = methodName;
		this.roles = roles;


		validateMethod(secureClass, this.methodName);
	}

	public Class getSecureClass() {
		return secureClass;
	}

	public String getClassName() {
		return secureClass.getName();
	}

	public String getMethodName() {
		return methodName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public boolean isAuthorised(Class clazz, String methodName, Set<String> clientRoles) {
		return isMethod(clazz, methodName) && hasRole(clientRoles);
	}

	public boolean isAuthorised(String className, String methodName, Set<String> clientRoles) {
		return isMethod(className, methodName) && hasRole(clientRoles);
	}

	public boolean isMethod(Class clazz, String methodName) {
		return isClass(clazz) && isMethodName(methodName);
	}

	public boolean isMethod(String className, String methodName) {
		return isClass(className) && isMethodName(methodName);
	}

	public boolean isClass(Class clazz) {
		return secureClass.isAssignableFrom(clazz);
	}

	public boolean isClass(String className) {
		return secureClass.getName().equals(className);
	}

	public boolean isMethodName(String methodName) {
		return this.methodName.equals(methodName);
	}

	public boolean hasRole(Set<String> clientRoles) {
		if (clientRoles == null) {
			return false;
		}
		return roles.stream().filter(r -> clientRoles.contains(r)).findFirst().isPresent();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SecureMethod that = (SecureMethod) o;
		return Objects.equals(secureClass, that.secureClass) &&
				Objects.equals(methodName, that.methodName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(secureClass.getName(), methodName);
	}

	@Override
	public String toString() {
		return new StringBuilder("SecureMethod{").
				append("secureClass=").append(secureClass.getSimpleName()).
				append(", methodName=").append(methodName).
				append(", roles=").append(roles).
				append("}").toString();
	}

	void validateMethod(Class serviceClass, String serviceMethodName) {
		try {
			if (!Arrays.asList(serviceClass.getMethods()).stream().
					filter(m -> m.getName().equals(serviceMethodName)).findFirst().isPresent()) {
				throw new IllegalArgumentException(format("Failed to find methodName=%s", serviceMethodName));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(
					format("Failed to validate method secureClass=%s does not have methodName=%s", serviceClass, serviceMethodName));
		}
	}
}
