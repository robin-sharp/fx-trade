package com.trade.fxtrade.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Service
public class KafkaFxTradePublisher {

	private String saveTopic = "fxtrade/save";

//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;
//
//	public void publish(FxTrade fxTrade) {
//		log.info("Publishing FxTrade {}", fxTrade.getTradeId());
//		kafkaTemplate.send(saveTopic, toJson(fxTrade));
//	}
}
