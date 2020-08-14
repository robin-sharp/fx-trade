package com.trade.security.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SecureMethodStore {
	private final Map<Integer, SecureMethod> methods = new ConcurrentHashMap();

	public Optional<SecureMethod> getSecureMethod(String className, String methodName) {
		return Optional.of(methods.get(Objects.hash(className, methodName)));
	}

	public void addSecureMethod(SecureMethod secureMethod) {
		methods.put(secureMethod.hashCode(), secureMethod);
	}
}
