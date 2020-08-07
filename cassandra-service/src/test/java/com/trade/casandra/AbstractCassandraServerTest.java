package com.trade.casandra;

import com.datastax.driver.core.Cluster;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base Class to start and stop the Embedded Cassandra Server
 */
public abstract class AbstractCassandraServerTest {

	private static final CassandraTestServer cassandraTestServer = new CassandraTestServer();

	@BeforeAll
	public static void startCassandraEmbedded() {
		cassandraTestServer.startTestCassandraServer();
	}

	@AfterAll
	public static void stopCassandraEmbedded() {
		cassandraTestServer.stopTestCassandraServer();
	}

	protected static Cluster getCluster() {
		return cassandraTestServer.getCluster();
	}
}
