package com.logitics.erp.payrollhistory.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.payroll.entity.Payroll;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollBasicSalaryHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id")
    private Payroll payroll;

    @Column(nullable = false)
    private String employeeNo;

    @Column(nullable = false)
    private int oldAmount;

    @Column(nullable = false)
    private int newAmount;
}
