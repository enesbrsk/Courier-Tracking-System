package com.demo.distance_service.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "courier", fetch = FetchType.LAZY)
    private CourierDistanceEntity distance;

    public CourierEntity(String id) {
        this.id = id;
    }
}
