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

    @Column(name = "client_timestamp", nullable = false)
    private long clientTimestamp;

    public CourierStoreEntryEntity(CourierEntity courier, String storeName, long clientTimestamp) {
        this.courier = courier;
        this.storeName = storeName;
        this.clientTimestamp = clientTimestamp;
    }

    public String getCourierId() {
        return courier.getId();
    }
}
