package com.trade.fxtrade;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import static com.trade.CurrencyTestData.GBP;
import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.*;
import static com.trade.entity.PartyTestData.COUNTERPARTY_CODE;
import static com.trade.entity.PartyTestData.PARTY_CODE;
import static com.trade.entity.UserTestData.LOGIN_NAME;
import static com.trade.fxtrade.FxTradeTestData.*;
import static com.trade.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FxTradeTest {

	@Test
	public void testFxTradeConstructor() {
		FxTrade fxTrade = FX_TRADE;
		assertEquals(FX_TRADE_UUID, fxTrade.getTradeId());
		assertEquals(FX, fxTrade.getTradeType());
		assertEquals(LOGIN_NAME, fxTrade.getTrader());
		assertEquals(PARTY_CODE, fxTrade.getParty());
		assertEquals(COUNTERPARTY_CODE, fxTrade.getCounterparty());
		assertEquals(FX_TRADE_STATUS_EXECUTED, fxTrade.getTradeStatus());
		assertEquals(EXECUTION_DATETIME, fxTrade.getExecutionTime());
		assertEquals(FX_BUY_SELL, fxTrade.getBuySell());
		assertEquals(ONE_MILLION, fxTrade.getAmount());
		assertEquals(GBP, fxTrade.getCurrency());
		assertEquals(GBP_USD, fxTrade.getCurrencyPair());
		assertEquals(FORWARD_DATE, fxTrade.getForwardDate());
		assertEquals(ONE_DOT_SIX_DIGITS, fxTrade.getRate());
		assertEquals("Fx 1000000.00 GBPUSD " + fxTrade.getForwardDate() + " @1.123456", fxTrade.getDescription());
	}

	@Test
	public void testFxTradeIsValid() {
		BeanPropertyBindingResult result = getValidationResult(FX_TRADE);
		assertEquals(0, result.getAllErrors().size());
	}

	@Test
	public void testFxTradeIsInvalidNullValues() {
		BeanPropertyBindingResult result = getValidationResult(FX_TRADE_NULL);
		assertEquals(11, result.getAllErrors().size());
	}

	@Test
	public void testFxTradeIsInvalidBlankValues() {
		BeanPropertyBindingResult result = getValidationResult(FX_TRADE_BLANK);
		assertEquals(5, result.getAllErrors().size());
	}

	@Test
	public void testFxTradeIsInvalidNegativeValues() {
		BeanPropertyBindingResult result = getValidationResult(FX_TRADE_NEGATIVE);
		assertEquals(2, result.getAllErrors().size());
	}

	@Test
	public void testFxTradeSerializable() {
		assertEquals(assertSerializable(FX_TRADE), FX_TRADE);
	}

	@Test
	public void testJsonFxTrade() {
		FxTrade input = FX_TRADE;
		assertJson(input, input.getClass());
	}

	@Test
	public void testEquals() {
		assertEquals(FX_TRADE, FX_TRADE);
		assertNotEquals(FX_TRADE, NEW_FX_TRADE());
		assertNotEquals(NEW_FX_TRADE(), FX_TRADE);
	}

	@Test
	public void testHashcode() {
		assertEquals(FX_TRADE.getTradeId().hashCode(), FX_TRADE.hashCode());
	}

	@Test
	public void testJson() {
		assertJson(FX_TRADE, FxTrade.class);
	}

	@Test
	public void validateFxTrade() {
		assertValid(FX_TRADE);
	}
}