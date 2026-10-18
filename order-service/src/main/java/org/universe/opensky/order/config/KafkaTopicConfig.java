package org.universe.opensky.order.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092,localhost:9093");
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic kafkaTopic() {
		return TopicBuilder.name("product-order-topic").build();
	}

	@Bean
	public NewTopic createTopic1() {
		Map<String, String> topicConfigs = new HashMap<>();
		topicConfigs.put("min.insync.replicas", "2");
		return TopicBuilder.name("product-order-topic1")
				.partitions(3)
				.replicas(2)
				.build().configs(topicConfigs);
	}

	@Bean
	public NewTopic createTopic2() {
		Map<String, String> topicConfigs = new HashMap<>();
		topicConfigs.put("min.insync.replicas", "2");
		return TopicBuilder.name("product-order-topic2")
				.partitions(3)
				.replicas(2)
				.build().configs(topicConfigs);
	}

}