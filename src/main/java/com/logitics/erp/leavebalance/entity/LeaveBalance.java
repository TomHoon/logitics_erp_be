package com.logitics.erp.leavebalance.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.leavetype.entity.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LeaveBalance extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveBalanceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leave_type_id")
	private LeaveType leaveType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	private Double totalDays;
	private Double usedDays;
	private Double remainDays;

	private LocalDate expireDate;

	public void useDays(Long days) {
		// 휴가사용시)
		// 1. 사용일 추가
		this.setUsedDays(this.getUsedDays() + days);

		// 2. 잔여 차감
		this.setRemainDays(this.getRemainDays() - days);
	}

}
