package com.logitics.erp.driver.entity;

import com.logitics.erp.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class Driver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @Column(nullable = false, length = 50)
    private String driverName;

    @Column(length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String status;
}
