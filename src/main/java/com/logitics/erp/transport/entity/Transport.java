package com.logitics.erp.transport.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.driver.entity.Driver;
import com.logitics.erp.vehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
public class Transport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    @Column(nullable = false, unique = true, length = 30)
    private String transportNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(nullable = false, length = 30)
    private String status;

    // 출발지
    @Column(nullable = false)
    private String departureAddress;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal departureLatitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal departureLongitude;

    // 도착지
    @Column(nullable = false)
    private String destinationAddress;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal destinationLatitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal destinationLongitude;

    // 현재 차량 위치
    @Column(precision = 10, scale = 7)
    private BigDecimal currentLatitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal currentLongitude;

    private LocalDateTime scheduledStartAt;

    private LocalDateTime startedAt;

    private LocalDateTime estimatedArrivalAt;

    private LocalDateTime completedAt;
}
