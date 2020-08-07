# Trade Server

The Trade Server is used to find and save trades.

## Swapping out Cassandra for an in memory cache

Cassandra can be swapped out for an in memory cache (MapFxTradeService) as follows:-

Change the following in the FxTradeServer:-

@SpringBootApplication(scanBasePackages={"com.trade.fxtrade.controller", "com.trade.fxtrade.cassandra", "com.trade.security"})
		
for the following:-

@SpringBootApplication(scanBasePackages={"com.trade.fxtrade.controller", "com.trade.fxtrade.cache", "com.trade.security"},
		exclude = {CassandraAutoConfiguration.class, CassandraRepositoriesAutoConfiguration.class, CassandraDataAutoConfiguration.class})

Change the the following in the FxTradeController:-

@Qualifier("CassandraFxTradeService")to @Qualifier("MapFxTradeService")