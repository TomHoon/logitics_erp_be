package com.logitics.erp.leavebalance.repository;

import com.logitics.erp.employee.entity.Employee;
import com.logitics.erp.leavebalance.entity.LeaveBalance;
import com.logitics.erp.leavetype.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
    List<LeaveBalance> findByEmployee(Employee e);

    boolean existsByEmployeeAndLeaveType(Employee employee, LeaveType annualLeave);
}
