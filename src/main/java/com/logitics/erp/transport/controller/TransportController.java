package com.logitics.erp.transport.controller;

import com.logitics.erp.common.util.KakaoRouteService;
import com.logitics.erp.transport.dto.KakaoRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transport")
public class TransportController {

    private final KakaoRouteService kakaoRouteService;

    @GetMapping("/test/routes")
    public KakaoRouteResponse testRoutes() {
        return kakaoRouteService.getRoute();
    }

}
