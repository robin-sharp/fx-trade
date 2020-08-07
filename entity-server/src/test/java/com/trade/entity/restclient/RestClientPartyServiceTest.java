package com.trade.entity.restclient;

import com.trade.entity.Party;
import com.trade.restclient.AbstractTestClient;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.entity.PartyTestData.NEW_PARTY;
import static com.trade.entity.PartyTestData.PARTY;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.*;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
class RestClientPartyServiceTest extends AbstractTestClient {

	private final String uri = "http://localhost:80";
	private final String path = uri + "/trade/v1/entity/party";
	private final String listPath = uri + "/trade/v1/entity/parties";

	@InjectMocks
	RestClientPartyService restClientPartyService;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void testSaveSuccessful() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				POST, getEntity(PARTY), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientPartyService.save(PARTY);
	}

	@Test
	void testSaveFailed() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				POST, getEntity(PARTY), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientPartyService.save(PARTY));

		assertEquals(format("Failed to save Party partyId=%s", PARTY.getPartyId()), exception.getMessage());
	}

	@Test
	void testSaveAllSuccessful() {
		List<Party> trades = Arrays.asList(NEW_PARTY(), NEW_PARTY());
		when(restTemplate.exchange(listPath,
				POST, getEntity(trades), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientPartyService.saveAll(trades);
	}

	@Test
	void testSaveAllFailed() {
		List<Party> fxTrades = Arrays.asList(NEW_PARTY(), NEW_PARTY());
		when(restTemplate.exchange(listPath,
				POST, getEntity(fxTrades), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientPartyService.saveAll(fxTrades));

		List<String> partyIds = fxTrades.stream().map(t -> t.getPartyId().toString()).collect(Collectors.toList());
		assertEquals(format("Failed to save Parties partyIds=%s", partyIds), exception.getMessage());
	}

	@Test
	void testDeleteSuccessful() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				DELETE, getEntity(), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientPartyService.delete(PARTY.getPartyId());
	}

	@Test
	void testDeleteFailed() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				DELETE, getEntity(), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientPartyService.delete(PARTY.getPartyId()));

		assertEquals(format("Failed to delete Party partyId=%s", PARTY.getPartyId()), exception.getMessage());
	}

	@Test
	void testGetByIdSuccessful() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				GET, getEntity(), Party.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientPartyService.getById(PARTY.getPartyId());
	}

	@Test
	void testGetByIdFailed() {
		when(restTemplate.exchange(path + "/" + PARTY.getPartyId(),
				GET, getEntity(), Party.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientPartyService.getById(PARTY.getPartyId()));

		assertEquals(format("Failed to get Party by Id partyId=%s", PARTY.getPartyId()), exception.getMessage());
	}

	@Test
	void testGetAllSuccessful() {
		Collection<Party> fxTrades = new ArrayList(Arrays.asList(new Party[]{NEW_PARTY(), NEW_PARTY()}));
		when(restTemplate.exchange(listPath,
				GET, getEntity(), Collection.class)).
				thenReturn(new ResponseEntity(fxTrades, HttpStatus.OK));

		Collection<Party> responsePartys = restClientPartyService.getAll();
		assertEquals(2, responsePartys.size());
	}

	@Test
	void testGetAllFailed() {
		Collection<Party> fxTrades = new ArrayList(Arrays.asList(new Party[]{NEW_PARTY(), NEW_PARTY()}));
		when(restTemplate.exchange(listPath,
				GET, getEntity(), Collection.class)).
				thenReturn(new ResponseEntity(fxTrades, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientPartyService.getAll());

		assertEquals("Failed to get All Parties", exception.getMessage());
	}
}