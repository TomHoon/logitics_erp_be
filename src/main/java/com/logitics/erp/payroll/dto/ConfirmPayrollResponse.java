package com.logitics.erp.payroll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmPayrollResponse {

    private int confirmedCount;

    private List<Map<String, Object>> failed;
}
