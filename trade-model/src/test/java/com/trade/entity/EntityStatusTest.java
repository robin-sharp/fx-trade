package com.trade.entity;

import org.junit.jupiter.api.Test;

import static com.trade.entity.EntityStatus.*;
import static com.trade.entity.PartyTestData.*;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityStatusTest {

	@Test
	public void testBuySellConstructor() {
		assertEquals(ENTITY_STATUS_C, CREATED.getShortcut());
		assertEquals(ENTITY_STATUS_P, PENDING.getShortcut());
		assertEquals(ENTITY_STATUS_A, ACTIVE.getShortcut());
		assertEquals(ENTITY_STATUS_S, SUSPENDED.getShortcut());
		assertEquals(ENTITY_STATUS_T, TERMINATED.getShortcut());
	}

	@Test
	public void testShortcut() {
		assertEquals(CREATED, getEntityStatus(ENTITY_STATUS_C));
		assertEquals(PENDING, getEntityStatus(ENTITY_STATUS_P));
		assertEquals(ACTIVE, getEntityStatus(ENTITY_STATUS_A));
		assertEquals(SUSPENDED, getEntityStatus(ENTITY_STATUS_S));
		assertEquals(TERMINATED, getEntityStatus(ENTITY_STATUS_T));
	}

	@Test
	public void testTradeStatusSerializable() {
		assertEquals(assertSerializable(ACTIVE), ACTIVE);
	}
}