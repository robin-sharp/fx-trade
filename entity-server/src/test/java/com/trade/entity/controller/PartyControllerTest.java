package com.trade.entity.controller;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.trade.EnvProfile.TEST_PROFILE;
import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.entity.PartyTestData.NEW_PARTY;
import static com.trade.entity.PartyTestData.PARTY;
import static com.trade.util.JsonUtil.toJson;
import static com.trade.util.TestUtil.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag(UNIT_TEST)
@Profile(TEST_PROFILE)
@ExtendWith(MockitoExtension.class)
public class PartyControllerTest {

	@Mock
	private PartyService partyService;

	@InjectMocks
	private PartyController partyController;

	@Test
	public void testSave() {
		partyController.save(PARTY);
		verify(partyService).save(PARTY);
	}

	@Test
	public void testGetById() {
		when(partyService.getById(PARTY.getPartyId())).thenReturn(PARTY);

		Party outputParty = partyController.getById(PARTY.getPartyId());

		verify(partyService).getById(PARTY.getPartyId());
		assertEquals(toJson(PARTY), toJson(outputParty));
	}

	@Test
	public void testGetAll() {
		Collection<Party> fxTrades = new ArrayList(Arrays.asList(new Party[]{NEW_PARTY(), NEW_PARTY()}));
		when(partyService.getAll()).thenReturn(fxTrades);

		Collection<Party> outputPartys = partyController.getAll();

		assertEquals(2, outputPartys.size());
		verify(partyService).getAll();
		assertJsonEquals(fxTrades, outputPartys);
	}

	@Test
	public void testDelete() {
		partyController.delete(PARTY.getPartyId());
		verify(partyService).delete(PARTY.getPartyId());
	}
}