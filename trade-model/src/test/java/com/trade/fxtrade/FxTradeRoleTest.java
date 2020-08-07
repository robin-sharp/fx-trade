package com.trade.fxtrade;

import org.junit.jupiter.api.Test;

import static com.trade.fxtrade.FxTradeRole.*;
import static com.trade.fxtrade.FxTradeTestData.*;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FxTradeRoleTest {
	@Test
	public void testBuySellConstructor() {
		assertEquals(FXTRADE_INTERNAL_FIND_ROLE_NAME, FXTRADE_INTERNAL_FIND_ROLE.getRoleName());
		assertEquals(FXTRADE_INTERNAL_SAVE_ROLE_NAME, FXTRADE_INTERNAL_SAVE_ROLE.getRoleName());
		assertEquals(FXTRADE_INTERNAL_DELETE_ROLE_NAME, FXTRADE_INTERNAL_DELETE_ROLE.getRoleName());
		assertEquals(FXTRADE_EXTERNAL_FIND_ROLE_NAME, FXTRADE_EXTERNAL_FIND_ROLE.getRoleName());
		assertEquals(FXTRADE_EXTERNAL_SAVE_ROLE_NAME, FXTRADE_EXTERNAL_SAVE_ROLE.getRoleName());
	}

	@Test
	public void testShortcut() {
		assertEquals(FXTRADE_INTERNAL_FIND_ROLE, getFxTradeRole(FXTRADE_INTERNAL_FIND_ROLE_NAME));
		assertEquals(FXTRADE_INTERNAL_SAVE_ROLE, getFxTradeRole(FXTRADE_INTERNAL_SAVE_ROLE_NAME));
		assertEquals(FXTRADE_INTERNAL_DELETE_ROLE, getFxTradeRole(FXTRADE_INTERNAL_DELETE_ROLE_NAME));
		assertEquals(FXTRADE_EXTERNAL_FIND_ROLE, getFxTradeRole(FXTRADE_EXTERNAL_FIND_ROLE_NAME));
		assertEquals(FXTRADE_EXTERNAL_SAVE_ROLE, getFxTradeRole(FXTRADE_EXTERNAL_SAVE_ROLE_NAME));
	}

	@Test
	public void testPartyRoleSerializable() {
		assertEquals(assertSerializable(FXTRADE_INTERNAL_FIND_ROLE), FXTRADE_INTERNAL_FIND_ROLE);
	}
}