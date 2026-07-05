package com.demo.courier_gateway_service;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class CourierGatewayServiceApplicationTests {

	@MockitoBean
	KafkaTemplate<String, CourierLocationEventDTO> kafkaTemplate;

	@Test
	void contextLoads() {
	}

}
