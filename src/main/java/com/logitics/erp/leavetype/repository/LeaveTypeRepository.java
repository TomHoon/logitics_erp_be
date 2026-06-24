package com.logitics.erp.leavetype.repository;

import com.logitics.erp.leavetype.entity.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    List<LeaveType> findByLeaveTypeName(String leaveTypeName);
}
