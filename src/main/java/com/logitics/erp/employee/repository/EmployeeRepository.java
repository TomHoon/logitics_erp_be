package com.logitics.erp.employee.repository;

import com.logitics.erp.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmployeeNo(String employeeNo);

	List<Employee> findByNameContaining(String name);

	Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment_DepartmentName(String departmentName);

	Optional<Employee> findByName(String nickname);
}
