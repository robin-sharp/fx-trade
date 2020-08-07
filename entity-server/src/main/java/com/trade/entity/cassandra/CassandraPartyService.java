package com.trade.entity.cassandra;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service("CassandraPartyService")
public class CassandraPartyService implements PartyService {

	@Autowired
	private CassandraPartyRepository partyRepository;

	@Override
	public void save(Party party) {
		log.info("save party={}", party);
		partyRepository.save(party);
	}

	@Override
	public void saveAll(List<? extends Party> parties) {
		log.info("saveAll parties={}", parties);
		partyRepository.saveAll(parties);
	}

	@Override
	public void delete(UUID partyId) {
		log.info("delete partyId={}", partyId);
		partyRepository.deleteById(partyId);
	}

	@Override
	public Party getById(UUID partyId) {
		log.info("getById partyId={}", partyId);
		return partyRepository.findById(partyId).orElse(null);
	}

	@Override
	public Collection<Party> getAll() {
		log.info("getAll");
		return partyRepository.findAll();
	}
}