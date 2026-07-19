package com.logitics.erp.common.init;

import com.logitics.erp.vehicle.entity.Vehicle;
import com.logitics.erp.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
@RequiredArgsConstructor
public class InitVehicleData implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() > 0) return;
        createVehicle();
    }

    public void createVehicle() {
        List<Vehicle> vehicles = List.of(
                Vehicle.builder()
                        .vehicleNumber("서울80아1234")
                        .vehicleType("1톤 카고")
                        .capacityTon(new BigDecimal("1.0"))
                        .status("AVAILABLE")
                        .build(),

                Vehicle.builder()
                        .vehicleNumber("서울81바2345")
                        .vehicleType("2.5톤 카고")
                        .capacityTon(new BigDecimal("2.5"))
                        .status("AVAILABLE")
                        .build(),

                Vehicle.builder()
                        .vehicleNumber("경기82사3456")
                        .vehicleType("5톤 카고")
                        .capacityTon(new BigDecimal("5.0"))
                        .status("AVAILABLE")
                        .build(),

                Vehicle.builder()
                        .vehicleNumber("인천83자4567")
                        .vehicleType("11톤 윙바디")
                        .capacityTon(new BigDecimal("11.0"))
                        .status("AVAILABLE")
                        .build(),

                Vehicle.builder()
                        .vehicleNumber("부산84아5678")
                        .vehicleType("25톤 카고")
                        .capacityTon(new BigDecimal("25.0"))
                        .status("AVAILABLE")
                        .build()
        );

        vehicleRepository.saveAll(vehicles);

    }
}
