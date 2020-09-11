package com.trade.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerConfig {

	@Value(value = "${bootstrapAddress}")
	private String bootstrapAddress;

	public String getBootstrapAddress() {
		return bootstrapAddress;
	}

	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> producerConfig = new HashMap<>();
		producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapAddress());
		producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return producerConfig;
	}
}
