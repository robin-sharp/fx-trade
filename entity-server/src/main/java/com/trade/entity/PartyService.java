package com.trade.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PartyService {
	void save(Party party);

	void saveAll(List<? extends Party> parties);

	void delete(UUID partyId);

	Party getById(UUID partyId);

	Collection<Party> getAll();
}
