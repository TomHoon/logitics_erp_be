package com.logitics.erp.payrollhistory.repository;

import com.logitics.erp.payrollhistory.entity.PayrollBasicSalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayrollBasicSalaryHistoryRepository extends JpaRepository<PayrollBasicSalaryHistory, Long> {
    List<PayrollBasicSalaryHistory> findByEmployeeNoOrderByCreatedAtDesc(String employeeNo);
}
