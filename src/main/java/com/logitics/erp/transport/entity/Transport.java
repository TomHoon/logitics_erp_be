package com.logitics.erp.transport.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.driver.entity.Driver;
import com.logitics.erp.vehicle.entity.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
public class Transport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Column(nullable = true, length = 30)
    @Builder.Default
    private String status = "REQUESTED";

    // 상차지
    @Column(nullable = false)
    private String pickupAddress;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLatitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal pickupLongitude;

    @Column(length = 50)
    private String pickupManagerName;

    @Column(length = 20)
    private String pickupManagerPhone;

    // 하차지
    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal deliveryLatitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal deliveryLongitude;

    @Column(length = 50)
    private String deliveryManagerName;

    @Column(length = 20)
    private String deliveryManagerPhone;

    // 현재 차량 위치
    @Column(precision = 10, scale = 7)
    private BigDecimal currentLatitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal currentLongitude;

    // 상차지 예정 도착 시간
    private LocalDateTime pickupScheduledAt;

    // 상차지 실제 도착 시간
    private LocalDateTime pickupArrivedAt;

    // 상차 완료 시간
    private LocalDateTime pickupCompletedAt;

    // 하차지 예정 도착 시간
    private LocalDateTime deliveryScheduledAt;

    // 하차지 실제 도착 시간
    private LocalDateTime deliveryArrivedAt;

    // 하차 완료 시간 = 운송 완료
    private LocalDateTime completedAt;
}
