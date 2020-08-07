package com.trade.fxtrade.cassandra;

import com.trade.fxtrade.FxTrade;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
//@NoRepositoryBean
public interface CassandraFxTradeRepository extends CassandraRepository<FxTrade, UUID> {
}
