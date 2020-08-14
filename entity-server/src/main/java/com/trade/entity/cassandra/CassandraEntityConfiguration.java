package com.trade.entity.cassandra;

import com.trade.cassandra.CassandraConfiguration;
import com.trade.entity.PartyRole;
import com.trade.security.service.SecureMethod;
import com.trade.security.service.SecureMethodStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import static com.trade.entity.PartyRole.PARTY_EXTERNAL_FIND_ROLE;
import static com.trade.entity.PartyRole.PARTY_INTERNAL_FIND_ROLE;

@Configuration
@ConfigurationProperties("cassandra.entity")
@EnableCassandraRepositories(basePackages = "com.trade.entity.cassandra")
public class CassandraEntityConfiguration extends CassandraConfiguration {

	@Autowired
	public void configureServiceMethods(SecureMethodStore secureMethodStore) {

		secureMethodStore.addSecureMethod(new SecureMethod(
				CassandraPartyService.class, "getByPartyCodeAndUser", PARTY_EXTERNAL_FIND_ROLE, PARTY_INTERNAL_FIND_ROLE));

		secureMethodStore.addSecureMethod(new SecureMethod(
				CassandraPartyService.class, "getByPartyCode", PartyRole.PARTY_SUPPORT_FIND_ROLE));
	}
}
