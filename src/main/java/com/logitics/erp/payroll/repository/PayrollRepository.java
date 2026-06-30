package com.logitics.erp.payroll.repository;

import com.logitics.erp.payroll.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

    Optional<Payroll> findByEmployee_EmployeeIdAndPayrollYearMonth(
            Long employeeId,
            int payrollYearMonth
    );

    List<Payroll> findByPayrollYearMonth(int payrollYearMonth);
}
