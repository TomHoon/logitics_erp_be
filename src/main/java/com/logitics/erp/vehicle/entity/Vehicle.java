package com.logitics.erp.vehicle.entity;

import com.logitics.erp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Vehicle extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(nullable = false, unique = true, length = 20)
    private String vehicleNumber;

    @Column(length = 30)
    private String vehicleType;

    @Column(precision = 5, scale = 1)
    private BigDecimal capacityTon;

    @Column(nullable = false, length = 20)
    private String status;
}
