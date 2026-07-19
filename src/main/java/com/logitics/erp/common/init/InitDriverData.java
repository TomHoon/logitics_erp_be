package com.logitics.erp.common.init;

import com.logitics.erp.driver.entity.Driver;
import com.logitics.erp.driver.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDriverData implements CommandLineRunner {

    private final DriverRepository driverRepository;

    @Override
    public void run(String... args) throws Exception {
        if (driverRepository.count() > 0) return;
        createDriverData();
    }

    public void createDriverData() {
        List<Driver> drivers = List.of(
                Driver.builder()
                        .driverName("김철수")
                        .phoneNumber("010-1111-1111")
                        .status("AVAILABLE")
                        .build(),

                Driver.builder()
                        .driverName("이영희")
                        .phoneNumber("010-2222-2222")
                        .status("AVAILABLE")
                        .build(),

                Driver.builder()
                        .driverName("박민수")
                        .phoneNumber("010-3333-3333")
                        .status("DRIVING")
                        .build(),

                Driver.builder()
                        .driverName("최지훈")
                        .phoneNumber("010-4444-4444")
                        .status("AVAILABLE")
                        .build(),

                Driver.builder()
                        .driverName("정우성")
                        .phoneNumber("010-5555-5555")
                        .status("OFF_DUTY")
                        .build()
        );

        driverRepository.saveAll(drivers);
    }
}
