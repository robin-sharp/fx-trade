package com.trade.fxtrade;

import com.trade.BuySell;
import com.trade.TradeStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static com.trade.CurrencyTestData.GBP_USD;
import static com.trade.TradeTestData.ONE_DOT_SIX_DIGITS;
import static com.trade.TradeTestData.ONE_MILLION;
import static com.trade.entity.PartyTestData.COUNTERPARTY_CODE;
import static com.trade.entity.PartyTestData.PARTY_CODE;
import static com.trade.entity.UserTestData.LOGIN_NAME;

public class FxTradeTestData {

	public final static BuySell FX_BUY_SELL = BuySell.BUY;

	public final static TradeStatus FX_TRADE_STATUS_EXECUTED = TradeStatus.EXECUTED;

	public final static LocalDateTime EXECUTION_DATETIME = LocalDateTime.now(ZoneOffset.UTC);
	public final static LocalDate FORWARD_DATE = LocalDate.now(ZoneOffset.UTC).plusDays(1);
	public final static UUID FX_TRADE_UUID = UUID.randomUUID();
	public final static FxPair FX_PAIR = new FxPair(GBP_USD, 4, 6);

	public final static FxTrade FX_TRADE = new FxTrade(
			FX_TRADE_UUID, LOGIN_NAME, PARTY_CODE, COUNTERPARTY_CODE, FX_TRADE_STATUS_EXECUTED, EXECUTION_DATETIME,
			FX_BUY_SELL, ONE_MILLION, GBP_USD, FORWARD_DATE, ONE_DOT_SIX_DIGITS);

	public final static FxTrade FX_TRADE_NULL = new FxTrade(
			null, null, null, null, null, null, null, null, null, null, null);

	public final static FxTrade FX_TRADE_BLANK = new FxTrade(
			FX_TRADE_UUID, "", "", "", FX_TRADE_STATUS_EXECUTED, EXECUTION_DATETIME,
			FX_BUY_SELL, ONE_MILLION, "", FORWARD_DATE, ONE_DOT_SIX_DIGITS);

	public final static FxTrade FX_TRADE_NEGATIVE = new FxTrade(
			FX_TRADE_UUID, LOGIN_NAME, PARTY_CODE, COUNTERPARTY_CODE, FX_TRADE_STATUS_EXECUTED, EXECUTION_DATETIME,
			FX_BUY_SELL, ONE_MILLION.negate(), GBP_USD, FORWARD_DATE, ONE_DOT_SIX_DIGITS.negate());

	public final static String FXTRADE_SUPPORT_FIND_ROLE_NAME = "FxTrade-Support-Find-Role";
	public final static String FXTRADE_SUPPORT_SAVE_ROLE_NAME = "FxTrade-Support-Save-Role";
	public final static String FXTRADE_SUPPORT_DELETE_ROLE_NAME = "FxTrade-Support-Delete-Role";
	public final static String FXTRADE_INTERNAL_FIND_ROLE_NAME = "FxTrade-Internal-Find-Role";
	public final static String FXTRADE_INTERNAL_SAVE_ROLE_NAME = "FxTrade-Internal-Save-Role";
	public final static String FXTRADE_EXTERNAL_FIND_ROLE_NAME = "FxTrade-External-Find-Role";
	public final static String FXTRADE_EXTERNAL_SAVE_ROLE_NAME = "FxTrade-External-Save-Role";

	public final static FxTrade NEW_FX_TRADE() {
		return NEW_FX_TRADE(UUID.randomUUID());
	}

	public final static FxTrade NEW_FX_TRADE(UUID uuid) {
		return new FxTrade(
				uuid, LOGIN_NAME, PARTY_CODE, COUNTERPARTY_CODE, FX_TRADE_STATUS_EXECUTED, EXECUTION_DATETIME,
				FX_BUY_SELL, ONE_MILLION, GBP_USD, FORWARD_DATE, ONE_DOT_SIX_DIGITS);
	}
}
