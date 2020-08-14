package com.trade.entity.cache;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.trade.entity.PartyTestData.NEW_PARTY;
import static com.trade.entity.PartyTestData.PARTY;
import static com.trade.util.TestUtil.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapPartyServiceTest {

	@Test
	void testSaveThenGetParty() {
		PartyService partyService = new MapPartyService();
		partyService.save(PARTY);
		assertEquals(PARTY, partyService.getById(PARTY.getPartyId()));
		assertEquals(PARTY, partyService.getByPartyCode(PARTY.getPartyCode()));
	}

	@Test
	void testSaveAllThenGetAllPartys() {
		PartyService partyService = new MapPartyService();
		List<Party> inputParties = Arrays.asList(new Party[]{NEW_PARTY(), NEW_PARTY(), NEW_PARTY()});
		partyService.saveAll(inputParties);
		Collection<Party> outputParties = partyService.getAll();
		assertJsonEquals(outputParties, inputParties);
	}

	@Test
	void testDeleteParty() {
		PartyService partyService = new MapPartyService();
		partyService.save(PARTY);
		partyService.delete(PARTY.getPartyId());
		assertNull(partyService.getById(PARTY.getPartyId()));
	}
}