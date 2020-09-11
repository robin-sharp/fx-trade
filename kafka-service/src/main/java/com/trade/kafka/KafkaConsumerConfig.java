package com.trade.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerConfig {
	@Value(value = "${bootstrapAddress}")
	private String bootstrapAddress;

	public String getBootstrapAddress() {
		return bootstrapAddress;
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> producerConfig = new HashMap<>();
		producerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapAddress());
		producerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return producerConfig;
	}
}
