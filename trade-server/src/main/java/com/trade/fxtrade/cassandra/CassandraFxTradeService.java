package com.trade.fxtrade.cassandra;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service("CassandraFxTradeService")
public class CassandraFxTradeService implements FxTradeService {

	//@Autowired
	private CassandraFxTradeRepository fxTradeRepository;

	@Override
	public void save(FxTrade fxTrade) {
		log.info("save fxTrade={}", fxTrade);
		fxTradeRepository.save(fxTrade);
	}

	@Override
	public void saveAll(List<? extends FxTrade> fxTrades) {
		log.info("saveAll trades={}", fxTrades);
		fxTradeRepository.saveAll(fxTrades);
	}

	@Override
	public void delete(UUID tradeId) {
		log.info("delete tradeId={}", tradeId);
		fxTradeRepository.deleteById(tradeId);
	}

	@Override
	public FxTrade getById(UUID tradeId) {
		log.info("getById tradeId={}", tradeId);
		return fxTradeRepository.findById(tradeId).orElse(null);
	}

	@Override
	public Collection<FxTrade> getAll() {
		log.info("getAll");
		return fxTradeRepository.findAll();
	}
}