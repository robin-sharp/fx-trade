package com.trade.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.lang.String.format;

public class JsonUtil {

	public static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
	}

	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new IllegalArgumentException(format("Failed to write %s", object.getClass().getSimpleName()), e);
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null) {
			return null;
		}
		if (clazz == null) {
			throw new IllegalArgumentException(format("Failed to read %s because no class set", json));
		}
		try {
			return objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			throw new IllegalArgumentException(format("Failed to read %s", json), e);
		}
	}

	public static <T> T fromMap(Map map, Class<T> clazz) {
		if (map == null) {
			return null;
		}
		if (clazz == null) {
			throw new IllegalArgumentException(format("Failed to read map %s because no class set", map));
		}
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			throw new IllegalArgumentException(format("Failed to read %s", map, clazz.getSimpleName()), e);
		}
	}

	public static Map<?, ?> toMap(String json) {
		if (json == null) {
			return null;
		}
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			throw new IllegalArgumentException(format("Failed to read %s", json), e);
		}
	}
}