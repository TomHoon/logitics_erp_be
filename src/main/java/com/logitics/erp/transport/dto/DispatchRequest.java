package com.logitics.erp.transport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchRequest {

    private String vehicleType;
    private LocalDateTime dispatchDateTime;
    
    // 요청자
    @Schema(description = "요청자")
    private String dispatchManager;

    @Schema(description = "상차지주소")
    private String departureLocation;

    @Schema(description = "상차지위도")
    private BigDecimal departureLat;

    @Schema(description = "상차지경도")
    private BigDecimal departureLng;

    @Schema(description = "상차지도착일시")
    private LocalDateTime departureArrivalTime;

    @Schema(description = "상차지담당자")
    private String departureMangerName;

    @Schema(description = "상차지연락처")
    private String departureMangerPhone;



    @Schema(description = "하차지주소")
    private String arrivalLocation;

    @Schema(description = "하차지위도")
    private BigDecimal arrivalLat;

    @Schema(description = "하차지경도")
    private BigDecimal arrivalLng;

    @Schema(description = "하차지도착일시")
    private LocalDateTime arrivalTime;

    @Schema(description = "하차지담당자")
    private String arrivalMangerName;

    @Schema(description = "하차지연락처")
    private String arrivalMangerPhone;

    @Schema(description = "배송물품")
    List<CargoItem> cargoList = new ArrayList<CargoItem>();

}
