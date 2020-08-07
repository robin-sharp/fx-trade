package com.trade.casandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
public class CassandraTestServer {

	private final ReentrantLock cassandraLock = new ReentrantLock();
	private Cluster cluster;
	private int refCount = 0;

	private int cassandraStartTimeout = 120000;
	private int cassandraReadTimeout = 10000;
	private int cassandraConnectTimeout = 10000;

	public void setCassandraStartTimeout(int cassandraTimeout) {
		this.cassandraStartTimeout = cassandraTimeout;
	}

	public void setCassandraReadTimeout(int cassandraConnectTimeout) {
		this.cassandraReadTimeout = cassandraReadTimeout;
	}

	public void setCassandraConnectTimeout(int cassandraConnectTimeout) {
		this.cassandraConnectTimeout = cassandraConnectTimeout;
	}

	public void startTestCassandraServer() {
		try {
			cassandraLock.tryLock(cassandraStartTimeout, MILLISECONDS);
			if (refCount++ == 0) {
				log.info("Starting CassandraTestServer");
				addShutdownHook();
				EmbeddedCassandraServerHelper.startEmbeddedCassandra(cassandraStartTimeout);
				cluster = Cluster.builder().
						withoutMetrics().
						withoutJMXReporting().
						addContactPoints("127.0.0.1").
						withPort(9142).
						withSocketOptions(
								new SocketOptions().
										setConnectTimeoutMillis(cassandraConnectTimeout).
										setReadTimeoutMillis(cassandraReadTimeout)).
						build();
				log.info("Started CassandraTestServer");
			}
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to start EmbeddedCassandraServer in %s millis", cassandraStartTimeout));
		} finally {
			cassandraLock.unlock();
		}
	}

	public Cluster getCluster() {
		try {
			cassandraLock.tryLock(cassandraStartTimeout, MILLISECONDS);
			return cluster;
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to get Cluster EmbeddedCassandraServer in %s millis", cassandraStartTimeout));
		} finally {
			cassandraLock.unlock();
		}
	}

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				cassandraLock.tryLock(cassandraStartTimeout, MILLISECONDS);
				if (cluster != null) {
					log.info("Shutting down CassandraTestServer");
					EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
					cluster = null;
					log.info("Shutdown CassandraTestServer");
				}
			} catch (Exception e) {
				throw new IllegalStateException(format("Failed to shutdown EmbeddedCassandraServer in %s mills", cassandraStartTimeout));
			} finally {
				cassandraLock.unlock();
			}
		}));
	}

	public void stopTestCassandraServer() {
		try {
			cassandraLock.tryLock(cassandraStartTimeout, MILLISECONDS);
			if (--refCount == 0) {
				log.info("Stopping CassandraTestServer");
				EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
				cluster = null;
				log.info("Stopped CassandraTestServer");
			}
		} catch (Exception e) {
			throw new IllegalStateException(format("Failed to stop EmbeddedCassandraServer in %s mills", cassandraStartTimeout));
		} finally {
			cassandraLock.unlock();
		}
	}
}
