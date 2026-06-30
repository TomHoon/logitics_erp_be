package com.logitics.erp.common.init;

import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.payroll.entity.Payroll;
import com.logitics.erp.payroll.repository.PayrollRepository;
import com.logitics.erp.payrolldetail.entity.PayrollDetail;
import com.logitics.erp.payrolldetail.mapper.PayrollDetailMapper;
import com.logitics.erp.payrolldetail.repository.PayrollDetailRepository;
import com.logitics.erp.payrollitem.entity.PayrollItemMaster;
import com.logitics.erp.payrollitem.repository.PayrollItemMasterRepository;
import com.logitics.erp.position.entity.Position;
import com.logitics.erp.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 5)
public class InitPayrollData implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;
    private final PayrollDetailRepository payrollDetailRepository;
    private final PayrollDetailMapper payrollDetailMapper;
    private final PayrollItemMasterRepository payrollItemMasterRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createTeamLeaderData();
    }

    @Transactional
    public void createTeamLeaderData() {
        // 1) Payroll이 먼저 있는지 확인하기
        Employee dhEmp = employeeRepository.findByEmail("dh@naver.com").orElse(null);
        if (dhEmp == null) {
            Department department = departmentRepository.findById(32L).orElse(null);
            Position p = positionRepository.findById(6L).orElse(null);


            dhEmp = Employee
                    .builder()
                    .employeeNo("T9950")
                    .position(p)
                    .department(department)
                    .name("동훈")
                    .email("dh@naver.com")
                    .birthDate(LocalDate.of(1995,6,27))
                    .phone("010-9079-2222")
                    .address("서울시 강서구 방화동")
                    .password(passwordEncoder.encode("1234"))
                    .build();

            employeeRepository.save(dhEmp);
//            throw new IllegalArgumentException("해당 회원 존재하지 않습니다.");
        }

        Payroll dhPayroll = payrollRepository.findByEmployee_EmployeeIdAndPayrollYearMonth(dhEmp.getEmployeeId(), 202606).orElse(null);

        if (dhPayroll != null) {
            // 이미 존재한다면 얼리리턴
            return;
        }


        // 2. [PayrollItemMaster 가져오기]
        // 2. 기본급
        PayrollItemMaster pimBasic = payrollItemMasterRepository.findByItemName("기본급");

        // 3. 식대
        PayrollItemMaster pimFood = payrollItemMasterRepository.findByItemName("식대");

        // 4. 교통비
        PayrollItemMaster pimTransportation = payrollItemMasterRepository.findByItemName("교통비");

        // 5. 직책수당(팀장)
        PayrollItemMaster pimResponsibility = payrollItemMasterRepository.findByItemName("직책수당");


        // ---------------------------------------------------------------

        // 5. [PayrollDetail 생성하기]


        // 5.2) payroll 급여명세서 없으면 생성하기
        if (dhPayroll == null) {
            Department dept = departmentRepository.findById(32L).orElseThrow();
            Position pos = positionRepository.findById(6L).orElseThrow();

            Payroll newDhPayroll = Payroll
                    .builder()
                    .employee(dhEmp)
                    .payrollYearMonth(Integer.parseInt(String.format("%02d", LocalDate.now().getYear(), LocalDate.now().getMonthValue())))
                    .paymentDate(LocalDate.of(2026, 6, 25))
                    .employeeNameSnapshot(dhEmp.getName())
                    .departmentNameSnapshot(dept.getDepartmentName())
                    .positionNameSnapshot(pos.getPositionName())
                    .totalPayAmount(BigDecimal.ZERO)
                    .totalDeductionAmount(BigDecimal.ZERO)
                    .realPayAmount(BigDecimal.ZERO)
                    .build();

            Payroll savedPayroll = payrollRepository.save(newDhPayroll);

            // 5.3) payroll에 붙은 payrollDetail 생성하기
            // 기본급 -> 식대 -> 교통비 -> 직책수당 순서
            PayrollDetail pdBasic = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(pimBasic)
                    .itemNameSnapshot(pimBasic.getItemName())
                    .itemTypeCodeSnapshot(pimBasic.getItemTypeCode())
                    .amount(3_750_000)
                    .build();

            PayrollDetail pdFood = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(pimFood)
                    .itemNameSnapshot(pimFood.getItemName())
                    .itemTypeCodeSnapshot(pimFood.getItemTypeCode())
                    .amount(200_000)
                    .build();

            PayrollDetail pdTransportation = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(pimTransportation)
                    .itemNameSnapshot(pimTransportation.getItemName())
                    .itemTypeCodeSnapshot(pimTransportation.getItemTypeCode())
                    .amount(150_000)
                    .build();

            PayrollDetail pdResponsibility = PayrollDetail
                    .builder()
                    .payroll(savedPayroll)
                    .payrollItemMaster(pimResponsibility)
                    .itemNameSnapshot(pimResponsibility.getItemName())
                    .itemTypeCodeSnapshot(pimResponsibility.getItemTypeCode())
                    .amount(200_000)
                    .build();

            payrollDetailRepository.saveAll(List.of(pdBasic, pdFood, pdTransportation, pdResponsibility));

        } else {
            // 5.3) payroll 급여명세서 있을 때 (더 복잡함)



        }
    }
}
