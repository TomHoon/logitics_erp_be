package com.logitics.erp.payments.service;

import com.logitics.erp.payments.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final RestTemplate restTemplate = new RestTemplate();

	public Object confirm(PaymentRequest request) {
		String encodedKey = Base64.getEncoder()
						.encodeToString(("test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6" + ":").getBytes(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Basic " + encodedKey);

		Map<String, Object> body = new HashMap<>();
		body.put("paymentKey", request.getPaymentKey());
		body.put("orderId", request.getOrderId());
		body.put("amount", request.getAmount());

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(
						"https://api.tosspayments.com/v1/payments/confirm",
						entity,
						Map.class
		);

		// 여기서 response.getBody() 확인 후 DB 저장
		return response.getBody();
	}
}
