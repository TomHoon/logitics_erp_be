package com.logitics.erp.transport.service;

import com.logitics.erp.driver.entity.Driver;
import com.logitics.erp.driver.repository.DriverRepository;
import com.logitics.erp.transport.dto.DispatchRequest;
import com.logitics.erp.transport.entity.Transport;
import com.logitics.erp.transport.repository.TransportRepository;
import com.logitics.erp.vehicle.entity.Vehicle;
import com.logitics.erp.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public Map<String,String> dispatchRequest(DispatchRequest request) {

        // 1. 테스트기사 찾기
        Driver d = driverRepository.findById(1L).orElse(null);
        if (d == null) throw new IllegalArgumentException("기사 배정 실패");
        
        // 2. 타입 설정
        Vehicle v = vehicleRepository.findById(1L).orElse(null);
        if (v == null) throw new IllegalArgumentException("차량 타입 확인 실패");
        
        Transport t = Transport.builder()
                .driver(d)
                .vehicle(v)

                // 상차지 위경도
                .pickupAddress(request.getDepartureLocation())
                .pickupLatitude(request.getDepartureLat())
                .pickupLongitude(request.getDepartureLng())

                .pickupScheduledAt(request.getDepartureArrivalTime())
                .pickupManagerName(request.getDepartureMangerName())
                .pickupManagerPhone(request.getDepartureMangerPhone())

                //하차지 위경도
                .deliveryAddress(request.getArrivalLocation())
                .deliveryLatitude(request.getArrivalLat())
                .deliveryLongitude(request.getArrivalLng())

                .deliveryScheduledAt(request.getArrivalTime())
                .deliveryManagerName(request.getArrivalMangerName())
                .deliveryManagerPhone(request.getArrivalMangerPhone())

                .build();

        transportRepository.save(t);

        return Map.of("result", "배차성공");
    }

}
