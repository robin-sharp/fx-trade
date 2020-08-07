package com.trade.fxtrade.controller;

import com.trade.fxtrade.FxTrade;
import com.trade.security.http.HttpSecureMethodBuilder;
import com.trade.security.http.HttpSecureMethodStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Class used to secure FxTrade service.
 */

@Configuration
public class FxTradeSecurityConfiguration {

	private final static String FXTRADE_WEBSERVICE = "fx-trade-client";

	@Autowired
	public void configure(HttpSecureMethodStore httpSecureMethodStore) {
		httpSecureMethodStore.addHttpSecureMethods(FXTRADE_WEBSERVICE,
				new HttpSecureMethodBuilder().buildHttpSecureMethods(FxTradeController.class, FxTrade.class));
	}
}