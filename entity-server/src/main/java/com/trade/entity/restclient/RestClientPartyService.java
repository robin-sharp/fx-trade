package com.trade.entity.restclient;

import com.trade.entity.Party;
import com.trade.entity.PartyService;
import com.trade.restclient.AbstractRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.*;

@Slf4j
@Service("RestClientPartyService")
public class RestClientPartyService extends AbstractRestClient implements PartyService {

	//TODO
	private final String uri = "http://localhost:80";
	private final String path = uri + "/trade/v1/entity/party";
	private final String listPath = uri + "/trade/v1/entity/parties";

	@Autowired
	private RestTemplate restTemplate;

	public void save(Party party) {
		log.info("Saving party partyId={}", party.getPartyId());
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Party> requestUpdate = new HttpEntity(party, headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				path + "/" + party.getPartyId(), POST, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to save Party partyId=%s", party.getPartyId()));
		}
	}

	public void saveAll(List<? extends Party> partys) {
		List<String> partyIds = partys.stream().map(t -> t.getPartyId().toString()).collect(Collectors.toList());
		log.info("Saving party partyIds={}", partyIds);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Party> requestUpdate = new HttpEntity(partys, headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				listPath, POST, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to save Parties partyIds=%s", partyIds));
		}
	}

	public void delete(UUID partyId) {
		log.info("Deleting party partyId={}", partyId);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Party> requestUpdate = new HttpEntity(headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				path + "/" + partyId, DELETE, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to delete Party partyId=%s", partyId));
		}
	}

	public Party getById(UUID partyId) {
		log.info("Get party by Id partyId={}", partyId);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Party> requestUpdate = new HttpEntity(headers);

		ResponseEntity<Party> response = restTemplate.exchange(
				path + "/" + partyId, GET, requestUpdate, Party.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to get Party by Id partyId=%s", partyId));
		}

		return response.getBody();
	}

	public Collection<Party> getAll() {
		log.info("Get all party");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Collection<Party>> requestUpdate = new HttpEntity(headers);

		ResponseEntity<Collection> response = restTemplate.exchange(
				listPath, GET, requestUpdate, Collection.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException("Failed to get All Parties");
		}

		return response.getBody();
	}
}
