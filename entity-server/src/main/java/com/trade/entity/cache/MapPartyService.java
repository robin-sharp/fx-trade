package com.trade.entity.cache;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight In-Memory store that can be used as a replacement for a DB Service.
 * TODO Spring @Cache
 */
@Slf4j
@Service("MapPartyService")
public class MapPartyService implements PartyService {

	private final Map<UUID, Party> parties = new ConcurrentHashMap();

	@Override
	public void save(Party party) {
		log.info("save party={}", party);
		parties.put(party.getPartyId(), party);
	}

	@Override
	public void saveAll(List<? extends Party> parties) {
		log.info("saveAll parties={}", parties);
		parties.forEach(p -> this.parties.put(p.getPartyId(), p));
	}

	@Override
	public void delete(UUID partyId) {
		log.info("delete partyId={}", partyId);
		parties.remove(partyId);
	}

	@Override
	public Party getById(UUID partyId) {
		log.info("getById partyId={}", partyId);
		return parties.get(partyId);
	}

	@Override
	public Party getByPartyCode(String partyCode) {
		return parties.values().stream().filter(p -> p.getPartyCode().equals(partyCode)).findFirst().orElse(null);
	}

	@Override
	public Collection<Party> getAll() {
		return parties.values();
	}
}
