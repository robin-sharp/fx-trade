package com.trade.fxtrade.cache;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.trade.fxtrade.FxTradeTestData.FX_TRADE;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static com.trade.util.TestUtil.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapTradeServiceTest {

	@Test
	void testSaveThenGetFxTrade() {
		FxTradeService tradeService = new MapFxTradeService();
		tradeService.save(FX_TRADE);
		assertEquals(FX_TRADE, tradeService.getById(FX_TRADE.getTradeId()));
	}

	@Test
	void testSaveAllThenGetAllFxTrades() {
		FxTradeService tradeService = new MapFxTradeService();
		List<FxTrade> inputTrades = Arrays.asList(new FxTrade[]{NEW_FX_TRADE(), NEW_FX_TRADE(), NEW_FX_TRADE()});
		tradeService.saveAll(inputTrades);
		Collection<FxTrade> outputTrades = tradeService.getAll();
		assertJsonEquals(outputTrades, inputTrades);
	}

	@Test
	void testDeleteFxTrade() {
		FxTradeService tradeService = new MapFxTradeService();
		tradeService.save(FX_TRADE);
		tradeService.delete(FX_TRADE.getTradeId());
		assertNull(tradeService.getById(FX_TRADE.getTradeId()));
	}
}