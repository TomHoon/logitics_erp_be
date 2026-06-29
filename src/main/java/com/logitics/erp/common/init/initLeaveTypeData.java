package com.logitics.erp.common.init;

import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.leavebalance.entity.LeaveBalance;
import com.logitics.erp.leavebalance.repository.LeaveBalanceRepository;
import com.logitics.erp.leavetype.entity.LeaveType;
import com.logitics.erp.leavetype.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class initLeaveTypeData implements CommandLineRunner {

    private final LeaveTypeRepository leaveTypeRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) {

        createLeaveTypes();
        createEmployeeAnnualLeaves();
    }

    private void createLeaveTypes() {
        if (leaveTypeRepository.count() > 0) {
            return;
        }

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

    private void createEmployeeAnnualLeaves() {
        LeaveType annualLeave = leaveTypeRepository
                .findByLeaveTypeName("연차")
                .get(0);

        List<Employee> employeeList = employeeRepository.findAll();

        for (Employee employee : employeeList) {

            boolean alreadyExists =
                    leaveBalanceRepository
                            .existsByEmployeeAndLeaveType(employee, annualLeave);

            if (alreadyExists) {
                continue;
            }

            LeaveBalance leaveBalance = LeaveBalance.builder()
                    .employee(employee)
                    .leaveType(annualLeave)
                    .totalDays(annualLeave.getDefaultDays())
                    .usedDays(0.0)
                    .remainDays(annualLeave.getDefaultDays())
                    .build();

            leaveBalanceRepository.save(leaveBalance);
        }
    }
}
