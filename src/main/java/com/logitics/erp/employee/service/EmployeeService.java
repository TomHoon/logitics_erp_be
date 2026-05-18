package com.logitics.erp.employee.service;

import com.logitics.erp.department.entity.Department;
import com.logitics.erp.department.repository.DepartmentRepository;
import com.logitics.erp.employee.dto.CreateEmployeeRequest;
import com.logitics.erp.employee.dto.CreateEmployeeResponse;
import com.logitics.erp.employee.dto.EmployeeListResponse;
import com.logitics.erp.employee.dto.SearchEmployeeRequest;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.mapper.EmployeeMapper;
import com.logitics.erp.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	private final EmployeeMapper employeeMapper;

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


	public List<EmployeeListResponse> getEmployees(int page, int size, SearchEmployeeRequest request) {

		int offset = page * 10;
		List<EmployeeListResponse> list = employeeMapper.getEmployees(size, offset, request);

		return list;
	}

	public List<Employee> getTest() {
		return employeeMapper.getTest();
	}
}
