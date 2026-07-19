package com.logitics.erp.transport.controller;

import com.logitics.erp.common.util.KakaoRouteService;
import com.logitics.erp.transport.dto.DispatchRequest;
import com.logitics.erp.transport.dto.KakaoRouteResponse;
import com.logitics.erp.transport.service.TransportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transport")
public class TransportController {

    private final KakaoRouteService kakaoRouteService;
    private final TransportService transportService;

    @GetMapping("/test/routes")
    public KakaoRouteResponse testRoutes() {
        return kakaoRouteService.getRoute();
    }

    @PostMapping("/dispatch-request")
    public Map<String, String> dispatchRequest(@RequestBody DispatchRequest request) {
        return transportService.dispatchRequest(request);
    }

}
