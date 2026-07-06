package com.demo.courier_gateway_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI courierTrackingOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Courier Tracking Gateway API")
                        .description("Ingests courier location pings and proxies read queries to downstream services.")
                        .version("1.0.0"));
    }
}
