package com.trade.fxtrade.controller;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
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
import static com.trade.fxtrade.FxTradeTestData.FX_TRADE;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static com.trade.util.JsonUtil.toJson;
import static com.trade.util.TestUtil.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag(UNIT_TEST)
@Profile(TEST_PROFILE)
@ExtendWith(MockitoExtension.class)
public class FxTradeControllerTest {

	@Mock
	private FxTradeService fxTradeService;

	@InjectMocks
	private FxTradeController fxTradeController;

	@Test
	public void testSaveFxTrade() {
		fxTradeController.save(FX_TRADE);
		verify(fxTradeService).save(FX_TRADE);
	}

	@Test
	public void testGetFxTradeById() {
		when(fxTradeService.getById(FX_TRADE.getTradeId())).thenReturn(FX_TRADE);

		FxTrade outputFxTrade = fxTradeController.getById(FX_TRADE.getTradeId());

		verify(fxTradeService).getById(FX_TRADE.getTradeId());
		assertEquals(toJson(FX_TRADE), toJson(outputFxTrade));
	}

	@Test
	public void testGetAllFxTrades() {
		Collection<FxTrade> fxTrades = new ArrayList(Arrays.asList(new FxTrade[]{NEW_FX_TRADE(), NEW_FX_TRADE()}));
		when(fxTradeService.getAll()).thenReturn(fxTrades);

		Collection<FxTrade> outputFxTrades = fxTradeController.getAll();

		assertEquals(2, outputFxTrades.size());
		verify(fxTradeService).getAll();
		assertJsonEquals(fxTrades, outputFxTrades);
	}

	@Test
	public void testDeleteFxTrade() {
		fxTradeController.delete(FX_TRADE.getTradeId());
		verify(fxTradeService).delete(FX_TRADE.getTradeId());
	}
}