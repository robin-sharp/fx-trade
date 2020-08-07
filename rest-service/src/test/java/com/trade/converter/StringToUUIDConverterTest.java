package com.trade.converter;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringToUUIDConverterTest {

	@Test
	public void testStringToUUIDConversion() {
		UUID inputUUID = UUID.randomUUID();
		UUID outputUUID = new StringToUUIDConverter().convert(inputUUID.toString());
		assertEquals(inputUUID, outputUUID);
	}
}
