package com.trade.fxtrade.cache;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight In-Memory store that can be used as a replacement for a DB Service
 */
@Slf4j
@Service("MapFxTradeService")
public class MapFxTradeService implements FxTradeService {

	private final Map<UUID, FxTrade> fxTrades = new ConcurrentHashMap();

	@Override
	public void save(FxTrade fxTrade) {
		log.info("save fxTrade={}", fxTrade);
		fxTrades.put(fxTrade.getTradeId(), fxTrade);
	}

	@Override
	public void saveAll(List<? extends FxTrade> fxTrades) {
		log.info("saveAll fxTrades={}", fxTrades);
		fxTrades.forEach((t) -> this.fxTrades.put(t.getTradeId(), t));
	}

	@Override
	public void delete(UUID tradeId) {
		log.info("delete tradeId={}", tradeId);
		fxTrades.remove(tradeId);
	}

	@Override
	public FxTrade getById(UUID tradeId) {
		log.info("getById tradeId={}", tradeId);
		return fxTrades.get(tradeId);
	}

	@Override
	public Collection<FxTrade> getAll() {
		log.info("getAll");
		return fxTrades.values();
	}
}
