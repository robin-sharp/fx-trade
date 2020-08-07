package com.trade.entity.cassandra;

import com.trade.cassandra.CassandraConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@ConfigurationProperties("cassandra.entity")
@EnableCassandraRepositories(basePackages = "com.trade.entity.cassandra")
public class CassandraEntityConfiguration extends CassandraConfiguration {
}
