package com.logitics.erp.payroll.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.payroll.dto.PayrollStatusCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Payroll extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payrollId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(nullable = false)
	private int payrollYearMonth;

    @Schema(description = "급여일")
	private LocalDate paymentDate;

	@Column(nullable = false)
	private BigDecimal totalPayAmount = BigDecimal.ZERO;;

	@Column(nullable = false)
	private BigDecimal totalDeductionAmount = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal realPayAmount = BigDecimal.ZERO;

	@Column(length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PayrollStatusCode payrollStatusCode = PayrollStatusCode.DRAFT;

	@Column(length = 100)
	private String employeeNameSnapshot;

	@Column(length = 100)
	private String departmentNameSnapshot;

	@Column(length = 100)
	private String positionNameSnapshot;

	public void setPayrollStatusCode(PayrollStatusCode payrollStatusCode) {
		this.payrollStatusCode = payrollStatusCode;
	}

}
