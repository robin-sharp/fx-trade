package com.trade.fxtrade.controller;

import com.trade.casandra.AbstractCassandraServerTest;
import com.trade.casandra.CassandraDataSet;
import com.trade.controller.ResponseExceptionHandler;
import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeServer;
import com.trade.fxtrade.cassandra.CassandraFxTradeConfiguration;
import org.cassandraunit.CQLDataLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.trade.EnvProfile.TEST_PROFILE;
import static com.trade.JUnitTag.SYSTEM_TEST;
import static com.trade.fxtrade.FxTradeTestData.NEW_FX_TRADE;
import static com.trade.util.JsonUtil.fromJson;
import static com.trade.util.JsonUtil.toJson;
import static com.trade.util.TestUtil.assertJsonEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: security causing 405
 */

@Tag(SYSTEM_TEST)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
		FxTradeServer.class,
		CassandraFxTradeConfiguration.class,
		ResponseExceptionHandler.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
@ActiveProfiles(TEST_PROFILE)
public class FxTradeControllerST extends AbstractCassandraServerTest {

	private final String path = "/trade/v1/fx/trade/fxtrade";
	private final String paths = "/trade/v1/fx/trade/fxtrades";

	private MockMvc mockMvc;
	@Autowired
	private FxTradeController fxTradeController;
	@Autowired
	private WebApplicationContext wac;

	@BeforeAll
	public static void beforeAll() {
		CassandraDataSet dataSet = CassandraDataSet.builder().
				withKeyspaceName("fxtrade").
				withFile("cassandra/fxtrade/002-create_table_fxtrade.cql").
				build();

		new CQLDataLoader(getCluster().connect()).load(dataSet);
	}

	@BeforeEach
	public void before() {
		mockMvc = MockMvcBuilders.
				standaloneSetup(fxTradeController).
				setControllerAdvice(new ResponseExceptionHandler()).
				build();
	}

	@Test
	public void testPutFxTradeSuccessful() throws Exception {
		FxTrade fxTrade = NEW_FX_TRADE();
		mockMvc.perform(put(path + "/" + fxTrade.getTradeId().toString()).
				content(toJson(fxTrade)).
				contentType(APPLICATION_JSON)).
				andExpect(status().isOk());
	}

	@Test
	public void testPutThenPutThenGetAllFxTradeSuccessful() throws Exception {

		List<FxTrade> fxTrades = new ArrayList(Arrays.asList(new FxTrade[]{NEW_FX_TRADE(), NEW_FX_TRADE()}));

		mockMvc.perform(put(path + "/" + fxTrades.get(0).getTradeId().toString()).
				content(toJson(fxTrades.get(0))).
				contentType(APPLICATION_JSON)).
				andExpect(status().isOk());

		mockMvc.perform(put(path + "/" + fxTrades.get(1).getTradeId().toString()).
				content(toJson(fxTrades.get(1))).
				contentType(APPLICATION_JSON)).
				andExpect(status().isOk());

		Collection<FxTrade> responseFxTrades = fromJson(
				mockMvc.perform(get(paths).
						content(toJson(NEW_FX_TRADE())).
						contentType(APPLICATION_JSON)).
						andExpect(status().isOk()).
						andReturn().getResponse().getContentAsString(),
				Collection.class);

		assertJsonEquals(fxTrades, responseFxTrades);
	}

	@Test
	public void testPutThenGetFxTradeSuccessful() throws Exception {

		FxTrade fxTrade = NEW_FX_TRADE();

		mockMvc.perform(put(path + "/" + fxTrade.getTradeId().toString()).
				content(toJson(fxTrade)).
				contentType(APPLICATION_JSON)).
				andExpect(status().isOk());

		FxTrade responseTrade = fromJson(
				mockMvc.perform(get(path + "/" + fxTrade.getTradeId().toString())).
						andExpect(status().isOk()).
						andReturn().getResponse().getContentAsString(),
				FxTrade.class);

		assertEquals(toJson(fxTrade), toJson(responseTrade));
	}

	@Test
	public void testPutThenDeleteThenGetFxTradeSuccessful() throws Exception {

		FxTrade fxTrade = NEW_FX_TRADE();

		mockMvc.perform(put(path + "/" + fxTrade.getTradeId().toString()).
				content(toJson(fxTrade)).
				contentType(APPLICATION_JSON)).
				andExpect(status().isOk());

		mockMvc.perform(delete(path + "/" + fxTrade.getTradeId().toString())).
				andExpect(status().isOk());

		mockMvc.perform(get(path + "/" + fxTrade.getTradeId().toString())).
				andExpect(status().isOk()).
				andExpect(content().string(""));
	}
}