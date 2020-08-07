package com.trade.fxtrade.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@Service
public class KafkaFxTradeSubscriber {

	//@KafkaListener(topics = "tradefx/save", groupId = "group_id")
	public void consume(String fxTrade) {
		log.info("Consuming FxTrade {}", fxTrade);
	}
}
