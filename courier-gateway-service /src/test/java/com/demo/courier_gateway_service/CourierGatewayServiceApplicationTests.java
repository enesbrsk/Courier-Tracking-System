package com.demo.courier_gateway_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class CourierGatewayServiceApplicationTests {

	@MockitoBean
	KafkaTemplate<String, Object> kafkaTemplate;

	@Test
	void contextLoads() {
	}

}
