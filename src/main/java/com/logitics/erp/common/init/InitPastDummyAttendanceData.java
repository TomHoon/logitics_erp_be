package com.logitics.erp.common.init;

import com.logitics.erp.attendance.dto.AttendRequest;
import com.logitics.erp.attendance.entity.Attendance;
import com.logitics.erp.attendance.mapper.AttendanceMapper;
import com.logitics.erp.attendance.repository.AttendanceRepository;
import com.logitics.erp.attendance.service.AttendanceService;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitPastDummyAttendanceData implements CommandLineRunner {


    private final AttendanceService attendanceService;
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public void run(String... args) throws Exception {
        createMayData();
        createJuneData();
    }

    void createMayData() {
        // 4-1. 이미 등록되어 있으면 패스
        Employee emp = employeeRepository.findByEmail("hajin@naver.com").orElseThrow();
        LocalDate startMonth = LocalDate.of(2026, 05, 01).withDayOfMonth(1);
        LocalDate endMonth = startMonth.plusMonths(1);
        List<Attendance> alreadyExistedList = attendanceMapper.getAttendanceByMonth(startMonth, endMonth, emp.getEmployeeId());
        if (alreadyExistedList != null && alreadyExistedList.size() > 0) return;

        // 1. 5월 전체 일자 가져오기
        int countOfJune = LocalDate.of(2026, 05, 01).lengthOfMonth();
        List<String> list = new ArrayList<>(Collections.nCopies(countOfJune, "-"));

        // 2. 사원 전체조회
        List<Employee> employeeList = employeeRepository.findAll();

        // 3. 5월 일자별로 Attend 처리하기
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            int dayNumber = i + 1;

            // 4. 사원 일자별로 출근처리하기
            for (Employee e : employeeList) {

                Attendance a = Attendance
                        .builder()
                        .employee(e)
                        .workDate(LocalDate.of(2026, 5, dayNumber))
                        .checkInTime(LocalDateTime.of(2026, 5, dayNumber, 9, 0, 0))
                        .checkOutTime(LocalDateTime.of(2026, 5, dayNumber, 18, 0, 0))
                        .attendanceStatusCode("출근")
                        .build();

                attendanceRepository.save(a);
            }
        }
    }

    void createJuneData() {
        // 4-1. 이미 등록되어 있으면 패스
        Employee emp = employeeRepository.findByEmail("hajin@naver.com").orElseThrow();
        LocalDate startMonth = LocalDate.of(2026, 06, 01).withDayOfMonth(1);
        LocalDate endMonth = startMonth.plusMonths(1);
        List<Attendance> alreadyExistedList = attendanceMapper.getAttendanceByMonth(startMonth, endMonth, emp.getEmployeeId());
        if (alreadyExistedList != null && alreadyExistedList.size() > 0) return;

        // 1. 6월 전체 일자 가져오기
        int countOfJune = LocalDate.of(2026, 06, 01).lengthOfMonth();
        List<String> list = new ArrayList<>(Collections.nCopies(countOfJune, "-"));

        // 2. 사원 전체조회
        List<Employee> employeeList = employeeRepository.findAll();

        // 3. 6월 일자별로 Attend 처리하기
        for (int i = 0; i < list.size() - 3; i++) {
            String item = list.get(i);
            int dayNumber = i + 1;

            // 4. 사원 일자별로 출근처리하기
            for (Employee e : employeeList) {


                Attendance a = Attendance
                        .builder()
                        .employee(e)
                        .workDate(LocalDate.of(2026, 6, dayNumber))
                        .checkInTime(LocalDateTime.of(2026, 6, dayNumber, 9, 0, 0))
                        .checkOutTime(LocalDateTime.of(2026, 5, dayNumber, 18, 0, 0))
                        .attendanceStatusCode("출근")
                        .build();

                attendanceRepository.save(a);
            }
        }
    }
}
