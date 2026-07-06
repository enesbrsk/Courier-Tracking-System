package com.demo.store_service.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "couriers")
public class CourierEntity {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    public CourierEntity(String id) {
        this.id = id;
    }
}
