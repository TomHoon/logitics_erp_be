package com.logitics.erp.attendance.service;

import com.logitics.erp.attendance.dto.*;
import com.logitics.erp.attendance.entity.Attendance;
import com.logitics.erp.attendance.mapper.AttendanceMapper;
import com.logitics.erp.attendance.repository.AttendanceRepository;
import com.logitics.erp.attendance.util.AttendanceProperty;
import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.mapper.EmployeeMapper;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.leavebalance.entity.LeaveBalance;
import com.logitics.erp.leavebalance.mapper.LeaveBalanceMapper;
import com.logitics.erp.leavebalance.repository.LeaveBalanceRepository;
import com.logitics.erp.leaverequest.entity.LeaveRequest;
import com.logitics.erp.leaverequest.repository.LeaveRequestRepository;
import com.logitics.erp.leavetype.entity.LeaveType;
import com.logitics.erp.leavetype.repository.LeaveTypeRepository;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AttendanceService {

	private final AttendanceMapper attendanceMapper;
	private final AttendanceRepository attendanceRepository;
	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;
	private final AttendanceProperty attendanceProperty;
	private final LeaveRequestRepository leaveRequestRepository;
	private final LeaveTypeRepository leaveTypeRepository;
	private final LeaveBalanceRepository leaveBalanceRepository;
    private final LeaveBalanceMapper leaveBalanceMapper;
    private final DepartmentRepository departmentRepository;


	@Transactional
	public AttendResponse attend(AttendRequest attendRequest) {
		// 1. 유저 엔티티 찾기
		Employee employee = employeeRepository.findByEmployeeNo(attendRequest.getEmployeeNo()).orElseThrow();

		// 2. 오늘 출근했는지 확인
		Long employeeId = employee.getEmployeeId();
		Attendance employeeAttendance = attendanceMapper.findTodayAttendance(employeeId, LocalDate.now());

		if (employeeAttendance != null) {
			LocalDateTime todayCheckIn = employeeAttendance.getCheckInTime();
			String todayCheckInStringified = todayCheckIn.toString();

			if (StringUtils.isNotBlank(todayCheckInStringified)) {
				throw new RuntimeException("이미 출근처리 되었습니다.");
			}
		}


		LocalDateTime now = LocalDateTime.now();
		String statusCode = "출근";

		if (now.toLocalTime().isAfter(LocalTime.of(8, 15))) {
			statusCode = "지각";
		}

		// 3. 출근처리하기
		Attendance attendance = Attendance.builder()
						.employee(employee)
						.workDate(attendRequest.getWorkDate())
						.checkInTime(LocalDateTime.now())
						.workMinutes(0)
						.comment(attendRequest.getMemo())
						.attendanceStatusCode(statusCode)
						.build();

		Attendance savedAttendance = attendanceRepository.save(attendance);
		return new AttendResponse(savedAttendance);
	}

	public List<AttendanceResultResponse> getMonthAttendance(int size, int page, Long departmentId, String startDate) {
		int offset = page * 10;
		String endDate = LocalDate.now().plusMonths(1).toString();
		return attendanceMapper.getMonthAttendance(size, offset, departmentId, startDate, endDate);
	}

	public List<AttendanceDailyResponse> getAttendanceDaily(AttendanceDailyRequest request) {

		List<AttendanceDailyResponse> list = attendanceMapper.getAttendanceDaily(request);
		return list;
	}

	public AttendResponse checkout(@Valid AttendRequest attendRequest) {
		// 1. 유저 엔티티 찾기
		Employee employee = employeeRepository.findByEmployeeNo(attendRequest.getEmployeeNo()).orElseThrow();

		// 2. 오늘 퇴근했는지 확인
		Long employeeId = employee.getEmployeeId();
		Attendance employeeAttendance = attendanceMapper.findTodayAttendance(employeeId, LocalDate.now());
		employeeAttendance.setEmployee(employee);


		if (employeeAttendance != null) {
			LocalDateTime todayCheckout = employeeAttendance.getCheckOutTime();

			if (todayCheckout != null) {
				throw new RuntimeException("이미 퇴근 처리 되었습니다.");
			}
		}

		if (employeeAttendance == null) {
			throw new RuntimeException("출근하지 않은 직원입니다.");
		}

		// 3. 퇴근처리하기
		employeeAttendance.setCheckOutTime(LocalDateTime.now());

		// 4. 퇴근시간 5분 전 이상인 경우 퇴근하지 못하도록 처리
		LocalTime endTime = attendanceProperty.getEndTime();
		int endHour = endTime.getHour();
		int endMinute = endTime.getMinute();

		LocalTime now = LocalTime.now();
		int nowHour = now.getHour();
		int nowMinute = now.getHour();

		// 퇴근시간 검사1) 퇴근시간 되기 전인 경우
		if ((nowHour < endHour)) {
			throw new IllegalArgumentException("[ERR-CHECKOUT-001] 퇴근시간 5분 전 부터 퇴근처리가 가능합니다.");
		}

		// 퇴근시간 검사2) 퇴근 시간이지만 5분 보다 전인 경우
		if ((nowHour == endHour && (nowMinute < endMinute -5 ))) {
			throw new IllegalArgumentException("[ERR-CHECKOUT-002] 퇴근시간 5분 전 부터 퇴근처리가 가능합니다.");
		}

		employeeAttendance.setAttendanceStatusCode("퇴근");

		Attendance savedAttendance = attendanceRepository.save(employeeAttendance);

		return new AttendResponse(savedAttendance);
	}

	public List<AttendanceResultResponse> getMonthly(AttendanceMonthlyRequest request) {

		List<AttendanceResultResponse> resultList = new ArrayList<>();

		// 1. findDate 없으면 현재 날짜로 설정
		if (request.getFindDate() == null) {
            request.setFindDate(LocalDate.now());
		}

        LocalDate findDate = request.getFindDate();

		// 2. findDate에서 해당 월의 days 가져오기
		int days = findDate.lengthOfMonth();

        // 3.

		// 4. 해당 월의 Attendance 조회하여 days List에 채우기
		// 4-1) 전 사원 조회
        String deptName = request.getDepartmentName();

        if (deptName == null || "전체".equals(deptName)) {
            deptName = null;
        }

		List<Employee> allEmployeeList = (deptName == null || deptName.equals("전체")) ? employeeRepository.findAll() : employeeRepository.findByDepartment_DepartmentName(deptName);

		// 4-2) 전사원 순회 돌며 resultList에 미리 넣기
		for (Employee e : allEmployeeList) {
            // 4-3. findDate에서 해당 월의 days로 List 생성(기본값인 "-" 셋팅)
            List<String> daysList = new ArrayList<String>(Collections.nCopies(days, "-"));

			AttendanceResultResponse ar = new AttendanceResultResponse();
			ar.setName(e.getName());
			ar.setDepartmentName(e.getDepartment().getDepartmentName());

			// 4-3) 해당 월의 사원의 summary 값 넣어주기
//			Map<String, Object> summaryRes = attendanceMapper.getSummaryByEmployee(findDate, e.getEmployeeId());


            // 4-4) 해당 월의 사원 근태 조회
            LocalDate startMonth = findDate.withDayOfMonth(1);
            LocalDate endMonth = startMonth.plusMonths(1);


            List<Attendance> attendanceList = attendanceMapper.getAttendanceListByPeriod(startMonth, endMonth, e.getEmployeeId(), request.getDepartmentName());

            // 4-5) 해당 월의 사원 근태리스트 > 존재하는 것만 뽑아서 daysList에 넣어준다.
            for (Attendance a : attendanceList) {
                LocalDate workDate = a.getWorkDate();
                int dayOfMonth = workDate.getDayOfMonth();
                daysList.set(dayOfMonth - 1, a.getAttendanceStatusCode());
            }
            ar.setDays(daysList);

            // 4-6) 월 근태리스트 셋팅 끝난 사원 >  resultList에 추가
            // [{ name: '홍길동', daysList: ['출', '-', '-', '연차' ] }]
            resultList.add(ar);
		}



		// 5. Summary 뽑기 (개발보류)

		return resultList;
	}

	// 조퇴처리
    @Transactional
	public Map<String, String> earlyLeave(EarlyLeaveRequest request) {
		// 1. 조퇴시간 변환(LocalTime으로) ex) 14:00
		LocalTime earlyCheckoutTime = request.getEarlyLeaveTime().toLocalTime();
		LocalTime endTime = attendanceProperty.getEndTime();

		Employee e = employeeRepository.findByEmployeeNo(request.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));

		// 2. 이미 출근한 출근 데이터 찾기(현재일자)
//		Attendance a = attendanceRepository
//				.findByEmployee(e)
//				.orElseThrow(() -> new IllegalArgumentException("출근하지 않은 사원입니다."));

        Attendance a = attendanceMapper.findTodayAttendance(e.getEmployeeId(), LocalDate.now());

        if (a == null) {
            throw new IllegalArgumentException("출근하지 않은 사원입니다.");
        }
		
		// 3. 퇴근시간을 조퇴시간으로 설정
		a.setCheckOutTime(request.getEarlyLeaveTime());

		// 4. statusCode를 조퇴로 설정
		a.setAttendanceStatusCode("조퇴");

		// 5. 근무하지 않은 시간을 저장
		// (퇴근시간 - 조퇴시간) = 미근무시간
		long noWorkTime = Duration.between(earlyCheckoutTime, endTime).toMinutes();
		a.setEarlyLeaveMinutes(noWorkTime);

        // 6. 조퇴사유 내용 저장
        a.setComment(request.getReason());

        // 7. JPA로 호출하지 않아 Employee 엔티티 누락된거 넣어주기
        a.setEmployee(e);

		attendanceRepository.save(a);
		
		return Map.of("결과", "true", "내용", "조퇴처리완료");
	}


	// 휴가 신청
    @Transactional
	public Map<String, String> requestLeave(LeaveRequestDTO request) {

		/**
		 * 1. 신청내역추가
		 * 2. 잔여휴가차감
		 * 3. 출근데이터 해당일자 휴가처리
		 */

		Employee e = employeeRepository
				.findByEmployeeNo(request.getEmployeeNo())
				.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));


		LeaveType lt = leaveTypeRepository.findByLeaveTypeName("연차").get(0);

		Long days = ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate());
		if (days < 0) {
			throw new IllegalArgumentException("잘못된 파라미터입니다.");
		}

		// 1. 신청내역추가
		LeaveRequest lr = LeaveRequest
				.builder()
				.employee(e)
				.leaveType(lt)
				.startDate(request.getStartDate())
				.endDate(request.getEndDate())
				.leaveDays(days)
				.reason(request.getLeaveReason())
				.build();

		leaveRequestRepository.save(lr);
		
		
		// 2. 잔여휴가 차감
		// 2-1) 사원의 잔여휴가 찾기
		LeaveBalance lb = leaveBalanceRepository.findByEmployee(e).get(0);
		lb.useDays(days);

		leaveBalanceRepository.save(lb);
		
		
		// 3. 출근데이터 해당일자 데이터 연차처리
		LocalDate startDate = request.getStartDate();
		LocalDate endDate = request.getEndDate();
		List<LocalDate> list = startDate.datesUntil(endDate.plusDays(1)).toList();

		for (LocalDate d : list) {

            // 3.1) 해당일자에 이미 출근데이터 있으면 에러
            List<Attendance> specificDateList = attendanceMapper.findDataBySpecificDate(d, e.getEmployeeId());

            if (specificDateList != null && specificDateList.size() > 0) {
                throw new IllegalArgumentException("이미 휴가신청한 날짜에 근태(휴가 또는 출근)가 등록되어있습니다.");
            }

			// 3.2) 근태등록 안되어 있으면 추가
			Attendance leaveAttendance = Attendance
					.builder()
					.employee(e)
					.attendanceStatusCode("연차")
					.workDate(d)
					.build();

			attendanceRepository.save(leaveAttendance);
		}

		return Map.of("결과", "true", "내용", "연차처리가 정상 등록되었습니다.");
	}

    public AttendBasicInfoResponse getBasicInfo(String employeeNo) {

        // 1. employee 조회
        Employee e = employeeRepository.findByEmployeeNo(employeeNo).orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        // 2. 해당 사원의 휴가 정보 조회
        Long employeeId = e.getEmployeeId();
		AttendBasicInfoResponse result = leaveBalanceMapper.getBasicInfo(employeeId);
		result.setWorkStartTime(attendanceProperty.getStartTime().toString());
		result.setWorkEndTime(attendanceProperty.getEndTime().toString());
		return result;
    }
}

