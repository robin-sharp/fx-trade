package com.trade.entity.controller;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/trade/v1/entity")
public class PartyController implements PartyService {

	@Autowired
	@Qualifier("CassandraPartyService")
	//@Qualifier("MapPartyService")
	private PartyService partyService;

	@Override
	@PostMapping("/party")
	public void save(@RequestBody Party party) {
		log.info("save party={}", party);
		partyService.save(party);
	}

	@Override
	@PostMapping("/parties")
	public void saveAll(@RequestBody List<? extends Party> parties) {
		log.info("saveAll parties={}", parties);
		partyService.saveAll(parties);
	}

	@Override
	@DeleteMapping("/party/{partyId}")
	public void delete(@PathVariable UUID partyId) {
		log.info("delete partyId={}", partyId);
		partyService.delete(partyId);
	}

	@Override
	@GetMapping("/party/{partyId}")
	public Party getById(@PathVariable UUID partyId) {
		log.info("getById partyId={}", partyId);
		return partyService.getById(partyId);
	}

	@Override
	@GetMapping("/parties")
	public Collection<Party> getAll() {
		log.info("getAll parties");
		return partyService.getAll();
	}
}