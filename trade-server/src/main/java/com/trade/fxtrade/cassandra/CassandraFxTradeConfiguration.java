package com.trade.fxtrade.cassandra;

import com.trade.cassandra.CassandraConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cassandra.fxtrade")
public class CassandraFxTradeConfiguration extends CassandraConfiguration {
}
