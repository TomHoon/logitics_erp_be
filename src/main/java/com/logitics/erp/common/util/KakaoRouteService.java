package com.logitics.erp.common.util;

import com.logitics.erp.transport.dto.KakaoRouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class KakaoRouteService {

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    private final RestClient restClient;


    public KakaoRouteService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://apis-navi.kakaomobility.com")
                .build();
    }

    public KakaoRouteResponse getRoute() {
        KakaoRouteResponse result = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/directions")
                        .queryParam("origin", "126.789307660567,37.4797690205157")
                        .queryParam("destination", "126.81060964296609,37.56368922800772")
                        .queryParam("priority", "RECOMMEND")
                        .build())
                .header(
                        "Authorization",
                        "KakaoAK " + restApiKey
                )
                .retrieve()
                .body(KakaoRouteResponse.class);
        return result;
    }
}
