package com.logitics.erp.attendance.service;

import com.logitics.erp.attendance.dto.AttendRequest;
import com.logitics.erp.attendance.dto.AttendResponse;
import com.logitics.erp.attendance.dto.AttendanceDailyResponse;
import com.logitics.erp.attendance.dto.AttendanceResultResponse;
import com.logitics.erp.attendance.entity.Attendance;
import com.logitics.erp.attendance.mapper.AttendanceMapper;
import com.logitics.erp.attendance.repository.AttendanceRepository;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

	private final AttendanceMapper attendanceMapper;
	private final AttendanceRepository attendanceRepository;
	private final EmployeeRepository employeeRepository;

	@Transactional
	public AttendResponse attend(AttendRequest attendRequest) {
		Employee employee = employeeRepository.findByEmployeeNo(attendRequest.getEmployeeNo()).orElseThrow();

		LocalDateTime today = LocalDateTime.now();
		boolean isExistAlready = attendanceRepository.existsByEmployeeAndWorkDate(employee, today);
		if (isExistAlready) {
			throw new RuntimeException("이미 출근한 직원입니다.");
		}

		Attendance attendance = Attendance.builder()
						.employee(employee)
						.workDate(attendRequest.getWorkDate())
						.checkInTime(LocalDateTime.now())
						.workMinutes(0)
						.attendanceStatusCode(attendRequest.getAttendanceStatusCode())
						.build();

		Attendance savedAttendance = attendanceRepository.save(attendance);
		return new AttendResponse(savedAttendance);
	}

	public List<AttendanceResultResponse> getMonthAttendance(int size, int page, Long departmentId, String startDate) {
		int offset = page * 10;
		String endDate = LocalDate.now().plusMonths(1).toString();
		return attendanceMapper.getMonthAttendance(size, offset, departmentId, startDate, endDate);
	}

	public List<AttendanceDailyResponse> getAttendanceDaily() {

		List<AttendanceDailyResponse> list = attendanceMapper.getAttendanceDaily();
		return list;
	}
}

