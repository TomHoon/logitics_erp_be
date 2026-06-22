package com.logitics.erp.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequest {
	private String paymentKey;
	private String orderId;
	private String amount;
}
