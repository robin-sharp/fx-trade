package com.trade.restclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class AbstractRestClient {
	public HttpEntity getEntity() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity requestUpdate = new HttpEntity(headers);
		return requestUpdate;
	}

	public <T> HttpEntity<T> getEntity(T body) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<T> requestUpdate = new HttpEntity(body, headers);
		return requestUpdate;
	}
}
