package com.demo.distance_service.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "courier_distances")
public class CourierDistanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "courier_id", nullable = false, unique = true)
    private CourierEntity courier;

    @Column(name = "total_distance", nullable = false)
    private double totalDistance;

    @Column(name = "last_latitude")
    private Double lastLatitude;

    @Column(name = "last_longitude")
    private Double lastLongitude;

    @Column(name = "last_time")
    private Instant lastTime;

    public CourierDistanceEntity(CourierEntity courier) {
        this.courier = courier;
        this.totalDistance = 0;
    }

    public String getCourierId() {
        return courier.getId();
    }

    public void addDistance(double distance) {
        this.totalDistance += distance;
    }

    public void updateLastLocation(double latitude, double longitude, Instant time) {
        this.lastLatitude = latitude;
        this.lastLongitude = longitude;
        this.lastTime = time;
    }
}
