package com.trade.entity;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.trade.entity.PartyTestData.*;
import static com.trade.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class PartyTest {

	@Test
	public void testPartyConstructor() {
		Party party = PARTY;
		assertEquals(PARTY_UUID, party.getPartyId());
		assertEquals(PARTY_CODE, party.getPartyCode());
		assertEquals(PARTY_FULL_NAME, party.getFullName());
		assertEquals(PARTY_COUNTRY_CODE, party.getCountryCode());
		assertEquals(PARTY_CREATION_TIME, party.getCreationTime());
		assertEquals(PARTY_ENTITIY_STATUS, party.getEntityStatus());
		assertEquals(PARTY_ENTITY_STATUS_CHANGE_TIME, party.getStatusChangeTime());
		assertEquals(PARTY_EMAILS, party.getEmails());

		assertEquals(PARTY_UUID, party.getEntityId());
		assertEquals(Party.class.getSimpleName(), party.getEntityType());
		assertEquals(PARTY_CODE, party.getEntityCode());
	}

	@Test
	public void testPartyIsInvalidValues() {
		BeanPropertyBindingResult result = getValidationResult(PARTY_INVALID);
		assertEquals(3, result.getAllErrors().size());
	}

	@Test
	public void testPartyIsInvalidBlankValues() {
		BeanPropertyBindingResult result = getValidationResult(PARTY_BLANK);
		assertEquals(4, result.getAllErrors().size());
	}

	@Test
	public void testPartyIsInvalidNullValues() {
		BeanPropertyBindingResult result = getValidationResult(PARTY_NULL);
		assertEquals(6, result.getAllErrors().size());
	}

	@Test
	public void testPartySerializable() {
		assertEquals(assertSerializable(PARTY), PARTY);
	}

	@Test
	public void testJsonParty() {
		assertJson(PARTY, PARTY.getClass());
	}

	@Test
	public void testPartyToString() {
		assertTrue(PARTY.toString().matches("class=Party, partyId=(.*?), partyCode=rsl, fullName=Robin Sharp Ltd, countryCode=GB, creationTime=(.*?), entityStatus=ACTIVE, statusChangeTime=(.*?), emails=\\[robin.sharp@robinsharp.com, robin.sharp@robinsharp.co.uk\\]"));
	}

	@Test
	public void testPartyEquals() {
		assertEquals(PARTY, PARTY);
		assertNotEquals(PARTY, COUNTERPARTY);
		assertNotEquals(COUNTERPARTY, PARTY);
		assertEquals(PARTY.getPartyId().hashCode(), PARTY.hashCode());
	}

	@Test
	public void testHashCode() {
		assertEquals(PARTY.getPartyId().hashCode(), PARTY.hashCode());
	}

	@Test
	public void testJson() {
		assertJson(PARTY, Party.class);
	}

	@Test
	public void testPartyIsValid() {
		assertValid(PARTY);
	}
}
