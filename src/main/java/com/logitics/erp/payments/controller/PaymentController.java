package com.logitics.erp.payments.controller;

import com.logitics.erp.payments.dto.PaymentRequest;
import com.logitics.erp.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/confirm")
	public ResponseEntity<?> confirmPayment(@RequestBody PaymentRequest request) {
		Object result = paymentService.confirm(request);
		return ResponseEntity.ok(result);
	}
}
