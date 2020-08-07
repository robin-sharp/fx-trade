package com.trade.fxtrade.restclient;

import com.trade.fxtrade.FxTrade;
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
import static com.trade.fxtrade.FxTradeTestData.FX_TRADE;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.*;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
class RestClientFxTradeServiceTest extends AbstractTestClient {

	private final String uri = "http://localhost:80";
	private final String path = uri + "/trade/v1/fx/trade/fxtrade";
	private final String listPath = path + "s";

	@InjectMocks
	RestClientFxTradeService restClientFxTradeService;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void testSaveSuccessful() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				POST, getEntity(FX_TRADE), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientFxTradeService.save(FX_TRADE);
	}

	@Test
	void testSaveFailed() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				POST, getEntity(FX_TRADE), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientFxTradeService.save(FX_TRADE));

		assertEquals(format("Failed to save FxTrade tradeId=%s", FX_TRADE.getTradeId()), exception.getMessage());
	}

	@Test
	void testSaveAllSuccessful() {
		List<FxTrade> trades = Arrays.asList(NEW_FX_TRADE(), NEW_FX_TRADE());
		when(restTemplate.exchange(listPath,
				POST, getEntity(trades), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientFxTradeService.saveAll(trades);
	}

	@Test
	void testSaveAllFailed() {
		List<FxTrade> fxTrades = Arrays.asList(NEW_FX_TRADE(), NEW_FX_TRADE());
		when(restTemplate.exchange(listPath,
				POST, getEntity(fxTrades), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientFxTradeService.saveAll(fxTrades));

		List<String> tradeIds = fxTrades.stream().map(t -> t.getTradeId().toString()).collect(Collectors.toList());
		assertEquals(format("Failed to save FxTrades tradeIds=%s", tradeIds), exception.getMessage());
	}

	@Test
	void testDeleteSuccessful() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				DELETE, getEntity(), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientFxTradeService.delete(FX_TRADE.getTradeId());
	}

	@Test
	void testDeleteFailed() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				DELETE, getEntity(), Void.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientFxTradeService.delete(FX_TRADE.getTradeId()));

		assertEquals(format("Failed to delete FxTrade tradeId=%s", FX_TRADE.getTradeId()), exception.getMessage());
	}

	@Test
	void testGetByIdSuccessful() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				GET, getEntity(), FxTrade.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.OK));

		restClientFxTradeService.getById(FX_TRADE.getTradeId());
	}

	@Test
	void testGetByIdFailed() {
		when(restTemplate.exchange(path + "/" + FX_TRADE.getTradeId(),
				GET, getEntity(), FxTrade.class)).
				thenReturn(new ResponseEntity(null, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientFxTradeService.getById(FX_TRADE.getTradeId()));

		assertEquals(format("Failed to get FxTrade by Id tradeId=%s", FX_TRADE.getTradeId()), exception.getMessage());
	}

	@Test
	void testGetAllSuccessful() {
		Collection<FxTrade> fxTrades = new ArrayList(Arrays.asList(new FxTrade[]{NEW_FX_TRADE(), NEW_FX_TRADE()}));
		when(restTemplate.exchange(listPath,
				GET, getEntity(), Collection.class)).
				thenReturn(new ResponseEntity(fxTrades, HttpStatus.OK));

		Collection<FxTrade> responseFxTrades = restClientFxTradeService.getAll();
		assertEquals(2, responseFxTrades.size());
	}

	@Test
	void testGetAllFailed() {
		Collection<FxTrade> fxTrades = new ArrayList(Arrays.asList(new FxTrade[]{NEW_FX_TRADE(), NEW_FX_TRADE()}));
		when(restTemplate.exchange(listPath,
				GET, getEntity(), Collection.class)).
				thenReturn(new ResponseEntity(fxTrades, HttpStatus.FORBIDDEN));

		IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
				restClientFxTradeService.getAll());

		assertEquals("Failed to get All FxTrades", exception.getMessage());
	}
}