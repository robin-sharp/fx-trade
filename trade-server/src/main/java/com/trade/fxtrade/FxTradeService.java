package com.trade.fxtrade;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface FxTradeService {

	void save(FxTrade fxTrade);

	void saveAll(List<? extends FxTrade> fxTrades);

	void delete(UUID tradeId);

	FxTrade getById(UUID tradeId);

	Collection<FxTrade> getAll();
}