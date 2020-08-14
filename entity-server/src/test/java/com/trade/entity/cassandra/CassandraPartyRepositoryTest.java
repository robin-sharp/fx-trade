package com.trade.entity.cassandra;

import com.trade.casandra.AbstractCassandraServerTest;
import com.trade.casandra.CassandraDataSet;
import com.trade.entity.EntityServer;
import com.trade.entity.Party;
import org.cassandraunit.CQLDataLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.trade.JUnitTag.UNIT_TEST;
import static com.trade.entity.PartyTestData.NEW_PARTY;
import static com.trade.util.JsonUtil.toJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag(UNIT_TEST)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
		EntityServer.class,
		CassandraEntityConfiguration.class})
public class CassandraPartyRepositoryTest extends AbstractCassandraServerTest {

	@Autowired
	private CassandraPartyRepository partyRepository;

	@BeforeAll
	public static void beforeAll() {
		CassandraDataSet dataSet = CassandraDataSet.builder().
				withKeyspaceName("entity").
				withFile("cassandra/entity/002-create_table_tradeparty.cql").
				build();

		new CQLDataLoader(getCluster().connect()).load(dataSet);
	}

	@Test
	public void testSaveThenFindPartyById() {
		Party inputParty = NEW_PARTY();

		partyRepository.save(inputParty);
		Optional<Party> outputParty = partyRepository.findById(inputParty.getPartyId());
		assertEquals(toJson(inputParty), toJson(outputParty.get()));
	}

	@Test
	public void testSaveAllThenFindPartiesById() {
		List<Party> parties = Arrays.asList(NEW_PARTY(), NEW_PARTY());
		partyRepository.saveAll(parties);

		Optional<Party> outputParty0 = partyRepository.findById(parties.get(0).getPartyId());
		assertEquals(toJson(parties.get(0)), toJson(outputParty0.get()));

		Optional<Party> outputParty1 = partyRepository.findById(parties.get(1).getPartyId());
		assertEquals(toJson(parties.get(1)), toJson(outputParty1.get()));
	}

	@Test
	public void testSaveThenDeleteThenFindPartyById() {
		Party inputParty = NEW_PARTY();

		partyRepository.save(inputParty);
		Optional<Party> outputParty = partyRepository.findById(inputParty.getPartyId());
		assertEquals(toJson(inputParty), toJson(outputParty.get()));

		partyRepository.deleteById(inputParty.getPartyId());
		Optional<Party> deleteParty = partyRepository.findById(inputParty.getPartyId());
		assertFalse(deleteParty.isPresent());
	}
}
