package com.logitics.erp.attendance.mapper;

import com.logitics.erp.attendance.dto.*;
import com.logitics.erp.attendance.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AttendanceMapper {
	List<AttendanceResultResponse> getMonthAttendance(int size, int offset, Long departmentId, String startDate, String endDate);

	List<AttendanceDailyResponse> getAttendanceDaily(AttendanceDailyRequest request);

	List<AttendanceInfoListResponse> getAttendanceInfoList(String findDate);

	Map<String, Object> getSummaryByEmployee(LocalDate findDate, Long employeeId);

	Attendance findTodayAttendance(Long employeeId, LocalDate now);

    List<Attendance> findDataBySpecificDate(LocalDate findDate, Long employeeId);

    List<Attendance> getAttendanceListByPeriod(LocalDate startMonth, LocalDate endMonth, Long employeeId, String departmentName);
}
