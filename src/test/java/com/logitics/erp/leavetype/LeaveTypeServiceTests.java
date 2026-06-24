package com.logitics.erp.leavetype;

import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.leavebalance.entity.LeaveBalance;
import com.logitics.erp.leavebalance.repository.LeaveBalanceRepository;
import com.logitics.erp.leavetype.entity.LeaveType;
import com.logitics.erp.leavetype.repository.LeaveTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LeaveTypeServiceTests {

	@Autowired
	private LeaveTypeRepository leaveTypeRepository;

	@Autowired
	private LeaveBalanceRepository leaveBalanceRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	@DisplayName("1. 휴가정책 추가")
	public void createLeaveType() {
		List<LeaveType> list = List.of(
				new LeaveType("연차", true, 15.0, ""),
				new LeaveType("반차(오전)", true, 0.5, ""),
				new LeaveType("반차(오후)", true, 0.5, ""),
				new LeaveType("반반차", true, 0.25, ""),

				new LeaveType("병가", false, 0.0, ""),
				new LeaveType("공가", false, 0.0, ""),
				new LeaveType("예비군", false, 0.0, ""),
				new LeaveType("민방위", false, 0.0, ""),
				new LeaveType("출장", false, 0.0, ""),

				new LeaveType("결혼휴가", false, 5.0, ""),
				new LeaveType("출산휴가(여)", false, 90.0, ""),
				new LeaveType("배우자 출산휴가", false, 10.0, ""),
				new LeaveType("부모사망", false, 5.0, ""),
				new LeaveType("배우자/자녀사망", false, 5.0, ""),
				new LeaveType("형제자매 사망", false, 1.0, ""),

				new LeaveType("생리휴가", false, 1.0, ""),
				new LeaveType("특별휴가", false, 0.0, ""),
				new LeaveType("보상휴가", false, 0.0, "")
		);

		leaveTypeRepository.saveAll(list);
	}

	@Test
	@DisplayName("2. 직원 연차 설정")
	void createEmployeeHolidays() {

		// 1. 추가할 휴가타입 얻기
		LeaveType leaveType = leaveTypeRepository.findByLeaveTypeName("연차").get(0);


		// 2. 전사원 조회
		List<Employee> employeeList = employeeRepository.findAll();


		// 3. 전사원 연차 할당
		for (Employee e : employeeList) {
			LeaveBalance lb = LeaveBalance
					.builder()
					.employee(e)
					.leaveType(leaveType)
					.totalDays(leaveType.getDefaultDays())
					.usedDays(0.0)
					.remainDays(leaveType.getDefaultDays())
					.build();

			leaveBalanceRepository.save(lb);
		}
	}

}
