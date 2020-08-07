package com.trade.fxtrade.restclient;

import com.trade.restclient.RestClientConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("restclient.fxtrade")
public class RestClientFxTradeConfiguration extends RestClientConfiguration {
}
