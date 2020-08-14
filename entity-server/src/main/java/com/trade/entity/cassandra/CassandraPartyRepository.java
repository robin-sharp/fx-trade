package com.trade.entity.cassandra;

import com.trade.entity.Party;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CassandraPartyRepository extends CassandraRepository<Party, UUID> {
	Optional<Party> findByPartyCode(String partyCode);

	@Query("SELECT * FROM Party WHERE partyCode=?0 AND ?1 IN users")
	Optional<Party> findByPartyCodeAndUser(String partyCode, String user);
}
