package com.demo.store_service.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "courier_store_entries")
public class CourierStoreEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courier_id", nullable = false)
    private CourierEntity courier;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "time", nullable = false)
    private Instant time;

    public CourierStoreEntryEntity(CourierEntity courier, String storeName, Instant time) {
        this.courier = courier;
        this.storeName = storeName;
        this.time = time;
    }

    public String getCourierId() {
        return courier.getId();
    }
}
