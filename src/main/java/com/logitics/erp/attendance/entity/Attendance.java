package com.logitics.erp.attendance.entity;

import com.logitics.erp.common.entity.BaseEntity;
import com.logitics.erp.employee.entity.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

public class Attendance extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attendanceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@Column(nullable = false)
	private LocalDate workDate;

	@Schema(description = "출근시간")
	private LocalDateTime checkInTime;

	@Schema(description = "퇴근시간")
	private LocalDateTime checkOutTime;

	private Integer workMinutes;
	private Integer overtimeMinutes;
	private Integer nightWorkMinutes;
	private Integer lateMinutes;
	
	@Schema(description = "비근무시간")
	private Long earlyLeaveMinutes;

	@Column(length = 30)
	private String attendanceStatusCode;

	private String comment;


	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public void setAttendanceStatusCode(String attendanceStatusCode) {
		this.attendanceStatusCode = attendanceStatusCode;
	}

	public void setEarlyLeaveMinutes(Long time) {
		this.earlyLeaveMinutes = time;
	}

	public void setEmployee(Employee e) {
		this.employee = e;
	}

    public void setComment(String comment) {
        this.comment = comment;
    }
}
