package com.trade.fxtrade.restclient;

import com.trade.fxtrade.FxTrade;
import com.trade.fxtrade.FxTradeService;
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
@Service("RestClientFxTradeService")
public class RestClientFxTradeService extends AbstractRestClient implements FxTradeService {

	//TODO
	private final String uri = "http://localhost:80";
	private final String path = "/trade/v1/fx/trade/fxtrade";
	private final String listPath = path + "s";

	@Autowired
	private RestTemplate restTemplate;

	public void save(FxTrade fxTrade) {
		log.info("Saving fxTrade tradeId={}", fxTrade.getTradeId());
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<FxTrade> requestUpdate = new HttpEntity(fxTrade, headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				uri + path + "/" + fxTrade.getTradeId(), POST, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to save FxTrade tradeId=%s", fxTrade.getTradeId()));
		}
	}

	public void saveAll(List<? extends FxTrade> fxTrades) {
		List<String> tradeIds = fxTrades.stream().map(t -> t.getTradeId().toString()).collect(Collectors.toList());
		log.info("Saving fxTrade tradeIds={}", tradeIds);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<FxTrade> requestUpdate = new HttpEntity(fxTrades, headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				uri + listPath, POST, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to save FxTrades tradeIds=%s", tradeIds));
		}
	}

	public void delete(UUID tradeId) {
		log.info("Deleting fxTrade tradeId={}", tradeId);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<FxTrade> requestUpdate = new HttpEntity(headers);
		ResponseEntity<Void> response = restTemplate.exchange(
				uri + path + "/" + tradeId, DELETE, requestUpdate, Void.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to delete FxTrade tradeId=%s", tradeId));
		}
	}

	public FxTrade getById(UUID tradeId) {
		log.info("Get fxTrade by Id tradeId={}", tradeId);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<FxTrade> requestUpdate = new HttpEntity(headers);

		ResponseEntity<FxTrade> response = restTemplate.exchange(
				uri + path + "/" + tradeId, GET, requestUpdate, FxTrade.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException(format("Failed to get FxTrade by Id tradeId=%s", tradeId));
		}

		return response.getBody();
	}

	public Collection<FxTrade> getAll() {
		log.info("Get all fxTrade");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Collection<FxTrade>> requestUpdate = new HttpEntity(headers);

		ResponseEntity<Collection> response = restTemplate.exchange(
				uri + listPath, GET, requestUpdate, Collection.class);

		if (response.getStatusCode() != HttpStatus.OK) {
			throw new IllegalStateException("Failed to get All FxTrades");
		}

		return response.getBody();
	}
}
