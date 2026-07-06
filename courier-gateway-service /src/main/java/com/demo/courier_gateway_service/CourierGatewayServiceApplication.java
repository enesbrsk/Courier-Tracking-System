package com.demo.courier_gateway_service;

import com.demo.courier_gateway_service.model.KafkaTopicProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.demo")
@EnableConfigurationProperties(KafkaTopicProperties.class)
public class CourierGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierGatewayServiceApplication.class, args);
	}

}
