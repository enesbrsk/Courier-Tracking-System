package com.demo.store_service.registry;

import com.demo.store_service.model.Store;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@Slf4j
@Getter
@Component
public class StoreRegistry {

    private final List<Store> stores;

    public StoreRegistry() throws IOException {
        var mapper = new ObjectMapper();
        var resource = new ClassPathResource("stores.json");

        this.stores = List.copyOf(mapper.readValue(resource.getInputStream(), new TypeReference<List<Store>>() {}));

        log.info("Loaded {} stores.", stores.size());
    }
}