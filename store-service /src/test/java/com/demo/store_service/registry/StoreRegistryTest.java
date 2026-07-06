package com.demo.store_service.registry;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class StoreRegistryTest {

    @Test
    void loadsStoresFromClasspath() throws IOException {
        var registry = new StoreRegistry();
        registry.getStores();

        assertFalse(registry.getStores().isEmpty());
        assertEquals(5, registry.getStores().size());
        assertEquals("Ataşehir MMM Migros", registry.getStores().getFirst().name());
    }
}
