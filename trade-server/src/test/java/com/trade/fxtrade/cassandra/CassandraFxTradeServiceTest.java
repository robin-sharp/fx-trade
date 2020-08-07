package com.trade.fxtrade.cassandra;

import com.trade.fxtrade.FxTrade;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.fxtrade.FxTradeTestData.FX_TRADE;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static org.mockito.Mockito.verify;

@Tag(UNIT_TEST)
@ExtendWith(MockitoExtension.class)
public class CassandraFxTradeServiceTest {

	@InjectMocks
	private CassandraFxTradeService cassandraFxTradeService;

	@Mock
	private CassandraFxTradeRepository cassandraFxTradeRepository;

	@Test
	public void testSaveFxTrade() {
		cassandraFxTradeService.save(FX_TRADE);
		verify(cassandraFxTradeRepository).save(FX_TRADE);
	}

	@Test
	public void saveAllFxTrades() {
		List<FxTrade> trades = Arrays.asList(NEW_FX_TRADE(), NEW_FX_TRADE());
		cassandraFxTradeService.saveAll(trades);
		verify(cassandraFxTradeRepository).saveAll(trades);
	}

	@Test
	public void deleteFxTrade() {
		UUID tradeId = UUID.randomUUID();
		cassandraFxTradeService.delete(tradeId);
		verify(cassandraFxTradeRepository).deleteById(tradeId);
	}

	@Test
	public void TestGetFxTradeById() {
		UUID tradeId = UUID.randomUUID();
		cassandraFxTradeService.getById(tradeId);
		verify(cassandraFxTradeRepository).findById(tradeId);
	}

	@Test
	public void testGetAllFxTrades() {
		cassandraFxTradeService.getAll();
		verify(cassandraFxTradeRepository).findAll();
	}
}