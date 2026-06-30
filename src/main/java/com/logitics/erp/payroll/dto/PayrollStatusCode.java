package com.logitics.erp.payroll.dto;

public enum PayrollStatusCode {

    DRAFT("계산전"),
    CALCULATED("계산완료"),
    CONFIRMED("확정"),
    PAID("지급완료"),
    CANCELED("취소");

    private String text;

    PayrollStatusCode(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }


}
