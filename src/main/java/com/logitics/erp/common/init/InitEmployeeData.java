package com.logitics.erp.common.init;

import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.repository.EmployeeRepository;
import com.logitics.erp.position.entity.Position;
import com.logitics.erp.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(value = 3)
public class InitEmployeeData implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() > 0) return;
        createUserData();
    }

    // 1. 사원 등록 & 회원가입 데이터 생성
    private void createUserData() {
        record TestUser(String name, String email){}

        Department department = departmentRepository.findById(31L).orElseThrow(null);

        if (department == null) throw new IllegalArgumentException("부서가 존재하지 않습니다.");

        List<TestUser> list = List.of(
                new TestUser("리흔", "riheun@naver.com"),
                new TestUser("주안", "juan@naver.com"),
                new TestUser("예린", "yerin@naver.com"),
                new TestUser("정민", "jungmin@naver.com"),
                new TestUser("민성", "minsung@naver.com"),
                new TestUser("하진", "hajin@naver.com"),
                new TestUser("동훈", "dh@naver.com")
        );

        Position p = positionRepository.findById(1L).orElseThrow();

        for (int i = 0; i < list.size(); i++) {

            String employeeNo =
                    "T" + String.format("%04d", i);

            Employee employee = Employee.builder()
                    .employeeNo(employeeNo)
                    .name(list.get(i).name())
                    .birthDate(
                            LocalDate.of(
                                    1990 + (i % 10),
                                    (i % 12) + 1,
                                    (i % 28) + 1
                            )
                    )
                    .email(list.get(i).email())
                    .phone("010-1111-" + String.format("%04d", i))
                    .address("서울시 테스트구 " + i)
                    .employeeStatusCode("재직")
                    .department(department)
                    .bankName("농협")
                    .accountHolder(list.get(i).name())
                    .accountNumber("339-910124-24707")
                    .password(passwordEncoder.encode("1234"))
                    .position(p)
                    .build();

            employeeRepository.save(employee);
        }
    }

}
