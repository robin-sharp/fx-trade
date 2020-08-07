package com.trade.fxtrade.controller;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/trade/v1/fx/trade")
public class FxTradeController implements FxTradeService {

	@Autowired
	@Qualifier("CassandraFxTradeService")
	//@Qualifier("MapFxTradeService")
	private FxTradeService fxTradeService;

	@Override
	@PostMapping("/fxtrade")
	public void save(@RequestBody FxTrade fxTrade) {
		log.info("save fxTrade={}", fxTrade);
		fxTradeService.save(fxTrade);
	}

	@Override
	@PostMapping("/fxtrades")
	public void saveAll(@RequestBody List<? extends FxTrade> fxTrades) {
		log.info("saveAll fxTrades={}", fxTrades);
		fxTradeService.saveAll(fxTrades);
	}

	@Override
	@GetMapping("/fxtrade/{tradeId}")
	public FxTrade getById(@PathVariable UUID tradeId) {
		log.info("getById tradeId={}", tradeId);
		return fxTradeService.getById(tradeId);
	}

	@Override
	@GetMapping("/fxtrades")
	public Collection<FxTrade> getAll() {
		log.info("getAll fxTrades");
		return fxTradeService.getAll();
	}

	@Override
	@DeleteMapping("/fxtrade/{tradeId}")
	public void delete(@PathVariable UUID tradeId) {
		log.info("delete tradeId={}", tradeId);
		fxTradeService.delete(tradeId);
	}
}