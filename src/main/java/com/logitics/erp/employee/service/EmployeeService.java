package com.logitics.erp.employee.service;

import com.logitics.erp.common.util.JwtProvider;
import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.dto.*;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.mapper.EmployeeMapper;
import com.logitics.erp.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeMapper employeeMapper;
	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	public void createEmployeeTest() {
		Department d = departmentRepository.findById(1L).orElseThrow();

		Employee e = Employee.builder()
						.employeeNo("L001")
						.name("tomhoon")
						.birthDate(LocalDate.of(1995, 7, 7))
						.email("gnsdl@naver.com")
						.department(d)
						.build();

		employeeRepository.save(e);
	}

	@Transactional
	public CreateEmployeeResponse createEmployee(CreateEmployeeRequest request) {
		Department department = departmentRepository.findByDepartmentName(request.getDepartmentName()).orElseThrow();

		String employeeNumber = "L" + String.format("%04d", employeeRepository.count() + 1);

		Employee e = Employee.builder()
						.employeeNo(employeeNumber)
						.name(request.getName())
						.birthDate(request.getBirthDate())
						.email(request.getEmail())
						.phone(request.getPhone())
						.address(request.getAddress())
						.employeeStatusCode(request.getEmployeeStatusCode())
						.department(department)
						.build();

		Employee createdEntity = employeeRepository.save(e);
		return new CreateEmployeeResponse(createdEntity);
	}


	public List<EmployeeListResponse> getEmployees(SearchEmployeeRequest request) {

		int offset = request.getPage() * 10;
		List<EmployeeListResponse> list = employeeMapper.getEmployees(request.getSize(), offset, request);

		return list;
	}

	public List<Employee> getTest() {
		return employeeMapper.getTest();
	}

	public LoginResponse login(LoginRequest loginRequest) throws Exception {
		Employee loginEmployee = employeeRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
		String encryptedPassword = loginEmployee.getPassword();
		Boolean isSamePassword = passwordEncoder.matches(loginRequest.getPassword(), encryptedPassword);

		if (isSamePassword) {
			String accessToken = jwtProvider.createToken(loginRequest.getEmail());
			long expireIn = 1000 * 60 * 30;
			String name = loginEmployee.getName();
			String email = loginEmployee.getEmail();
			String employeeNo = loginEmployee.getEmployeeNo();
			String departmentName = loginEmployee.getDepartment().getDepartmentName();

			return LoginResponse.builder()
							.accessToken(accessToken)
							.name(name)
							.expireIn(expireIn)
							.email(email)
							.employeeNo(employeeNo)
							.departmentName(departmentName)
							.build();
		}

		throw new Exception("올바른 회원 정보가 아닙니다.");
	}
}
