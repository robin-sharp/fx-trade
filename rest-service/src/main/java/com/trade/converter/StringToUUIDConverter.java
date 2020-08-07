package com.trade.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUUIDConverter implements Converter<String, UUID> {
	public UUID convert(String source) {
		return UUID.fromString(source);
	}
}