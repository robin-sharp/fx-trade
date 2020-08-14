package com.trade.entity;

import org.junit.jupiter.api.Test;

import static com.trade.entity.PartyRole.*;
import static com.trade.entity.PartyTestData.*;
import static com.trade.util.TestUtil.assertSerializable;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartyRoleTest {

	@Test
	public void testBuySellConstructor() {
		assertEquals(PARTY_SUPPORT_FIND_ROLE_NAME, PARTY_SUPPORT_FIND_ROLE.getRoleName());
		assertEquals(PARTY_SUPPORT_SAVE_ROLE_NAME, PARTY_SUPPORT_SAVE_ROLE.getRoleName());
		assertEquals(PARTY_SUPPORT_DELETE_ROLE_NAME, PARTY_SUPPORT_DELETE_ROLE.getRoleName());
		assertEquals(PARTY_INTERNAL_FIND_ROLE_NAME, PARTY_INTERNAL_FIND_ROLE.getRoleName());
		assertEquals(PARTY_INTERNAL_SAVE_ROLE_NAME, PARTY_INTERNAL_SAVE_ROLE.getRoleName());
		assertEquals(PARTY_EXTERNAL_FIND_ROLE_NAME, PARTY_EXTERNAL_FIND_ROLE.getRoleName());
	}

	@Test
	public void testShortcut() {
		assertEquals(PARTY_SUPPORT_FIND_ROLE, getPartyRole(PARTY_SUPPORT_FIND_ROLE_NAME));
		assertEquals(PARTY_SUPPORT_SAVE_ROLE, getPartyRole(PARTY_SUPPORT_SAVE_ROLE_NAME));
		assertEquals(PARTY_SUPPORT_DELETE_ROLE, getPartyRole(PARTY_SUPPORT_DELETE_ROLE_NAME));
		assertEquals(PARTY_INTERNAL_FIND_ROLE, getPartyRole(PARTY_INTERNAL_FIND_ROLE_NAME));
		assertEquals(PARTY_INTERNAL_SAVE_ROLE, getPartyRole(PARTY_INTERNAL_SAVE_ROLE_NAME));
		assertEquals(PARTY_EXTERNAL_FIND_ROLE, getPartyRole(PARTY_EXTERNAL_FIND_ROLE_NAME));
	}

	@Test
	public void testPartyRoleSerializable() {
		assertEquals(assertSerializable(PARTY_INTERNAL_FIND_ROLE), PARTY_INTERNAL_FIND_ROLE);
	}

	@Test
	public void testPartyToString() {
		assertEquals(PARTY_SUPPORT_FIND_ROLE.getRoleName(), PARTY_SUPPORT_FIND_ROLE.toString());
	}
}
