package com.trade.entity.cassandra;

import com.trade.entity.Party;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

//@Repository
public interface CassandraPartyRepository extends CassandraRepository<Party, UUID> {
}
