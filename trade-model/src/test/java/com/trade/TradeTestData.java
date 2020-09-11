package com.trade;

import java.math.BigDecimal;

public class TradeTestData {

	public final static String FX = "Fx";

	public final static String BUY_SELL_B = "B";
	public final static String BUY_SELL_S = "S";

	public final static String TRADE_STATUS_E = "E";
	public final static String TRADE_STATUS_A = "A";
	public final static String TRADE_STATUS_F = "F";
	public final static String TRADE_STATUS_C = "C";
	public final static String TRADE_STATUS_S = "S";

	public final static BigDecimal ZERO = new BigDecimal("0");

	public final static BigDecimal ONE = new BigDecimal("1");
	public final static BigDecimal TWO = new BigDecimal("2");
	public final static BigDecimal THREE = new BigDecimal("3");
	public final static BigDecimal FOUR = new BigDecimal("4");
	public final static BigDecimal FIVE = new BigDecimal("5");
	public final static BigDecimal SIX = new BigDecimal("6");
	public final static BigDecimal SEVEN = new BigDecimal("7");
	public final static BigDecimal EIGHT = new BigDecimal("8");
	public final static BigDecimal NINE = new BigDecimal("9");
	public final static BigDecimal TEN = new BigDecimal("10");

	public final static BigDecimal ONE_TENTH = new BigDecimal("0.1");

	public final static BigDecimal ONE_HUNDREDTH = new BigDecimal("0.01");

	public final static BigDecimal ONE_MILLION = new BigDecimal("1000000.00");
	public final static BigDecimal TWO_MILLION = new BigDecimal("2000000.00");
	public final static BigDecimal THREE_MILLION = new BigDecimal("3000000.00");
	public final static BigDecimal FOUR_MILLION = new BigDecimal("4000000.00");
	public final static BigDecimal FIVE_MILLION = new BigDecimal("5000000.00");
	public final static BigDecimal TEN_MILLION = new BigDecimal("10000000.00");

	public final static BigDecimal ONE_DOT_SIX_DIGITS = new BigDecimal("1.123456");
	public final static BigDecimal ONE_DOT_ONE = new BigDecimal("1.100000");
	public final static BigDecimal ONE_DOT_TWO = new BigDecimal("1.200000");
	public final static BigDecimal ONE_DOT_THREE = new BigDecimal("1.300000");
	public final static BigDecimal ONE_DOT_FOUR = new BigDecimal("1.400000");
	public final static BigDecimal ONE_DOT_FIVE = new BigDecimal("1.500000");

	public final static BigDecimal BID = new BigDecimal("1.12345");
	public final static BigDecimal OFFER = new BigDecimal("1.12354");

	public final static BidOffer BID_OFFER = new BidOffer(BID, OFFER);

}
