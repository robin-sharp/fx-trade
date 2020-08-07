package com.trade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface Trade {
	UUID getTradeId();

	String getTradeType();

	String getTrader();

	String getParty();

	String getCounterparty();

	TradeStatus getTradeStatus();

	LocalDateTime getExecutionTime();

	BuySell getBuySell();

	BigDecimal getAmount();

	String getCurrency();

	String getDescription();
}
