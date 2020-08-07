package com.trade.fxtrade.cassandra;

import com.trade.casandra.AbstractCassandraServerTest;
import com.trade.casandra.CassandraDataSet;
import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeServer;
import org.cassandraunit.CQLDataLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static com.trade.util.JsonUtil.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag(UNIT_TEST)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
		FxTradeServer.class,
		CassandraFxTradeConfiguration.class})
public class CassandraFxTradeRepositoryTest extends AbstractCassandraServerTest {

	@Autowired
	private CassandraFxTradeRepository fxTradeRepository;

	@BeforeAll
	public static void beforeAll() {
		CassandraDataSet dataSet = CassandraDataSet.builder().
				withKeyspaceName("fxtrade").
				withFile("cassandra/fxtrade/002-create_table_fxtrade.cql").
				build();

		new CQLDataLoader(getCluster().connect()).load(dataSet);
	}

	@Test
	public void testSaveThenFindFxTradeById() {
		FxTrade inputFxTrade = NEW_FX_TRADE();

		fxTradeRepository.save(inputFxTrade);
		Optional<FxTrade> outputFxTrade = fxTradeRepository.findById(inputFxTrade.getTradeId());
		assertEquals(toJson(inputFxTrade), toJson(outputFxTrade.get()));
	}

	@Test
	public void testSaveAllThenFindFxTradesById() {
		List<FxTrade> trades = Arrays.asList(NEW_FX_TRADE(), NEW_FX_TRADE());
		fxTradeRepository.saveAll(trades);

		Optional<FxTrade> outputFxTrade0 = fxTradeRepository.findById(trades.get(0).getTradeId());
		assertEquals(toJson(trades.get(0)), toJson(outputFxTrade0.get()));

		Optional<FxTrade> outputFxTrade1 = fxTradeRepository.findById(trades.get(1).getTradeId());
		assertEquals(toJson(trades.get(1)), toJson(outputFxTrade1.get()));
	}

	@Test
	public void testSaveThenDeleteThenFindFxTradeById() {
		FxTrade inputFxTrade = NEW_FX_TRADE();

		fxTradeRepository.save(inputFxTrade);
		Optional<FxTrade> outputFxTrade = fxTradeRepository.findById(inputFxTrade.getTradeId());
		assertEquals(toJson(inputFxTrade), toJson(outputFxTrade.get()));

		fxTradeRepository.delete(inputFxTrade);
		Optional<FxTrade> deleteFxTrade = fxTradeRepository.findById(inputFxTrade.getTradeId());
		assertFalse(deleteFxTrade.isPresent());
	}
}