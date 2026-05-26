package com.logitics.erp.employee.controller;

import com.logitics.erp.employee.dto.CreateEmployeeRequest;
import com.logitics.erp.employee.dto.CreateEmployeeResponse;
import com.logitics.erp.employee.dto.EmployeeListResponse;
import com.logitics.erp.employee.dto.SearchEmployeeRequest;
import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

	private final EmployeeService employeeService;

	@GetMapping("/createTest")
	public void createTest() {
		employeeService.createEmployeeTest();
	}

	@GetMapping("/getTest")
	public List<Employee> getTest() {
		return employeeService.getTest();
	}

	@GetMapping
	@Tag(name = "Employee", description = "사원 관리 API")
	@Operation(summary = "사원 조회", description = "사원 목록을 조회합니다.")
	public List<EmployeeListResponse> getEmployees(
					@Parameter(
									description = "사번/사원명으로조회",
									example = "이채리1"
					)
					SearchEmployeeRequest request
	) {
		return employeeService.getEmployees(request);
	}

	@PostMapping
	public CreateEmployeeResponse createEmployee(
					@RequestBody @Valid CreateEmployeeRequest request
	) {
		return employeeService.createEmployee(request);
	}

}
