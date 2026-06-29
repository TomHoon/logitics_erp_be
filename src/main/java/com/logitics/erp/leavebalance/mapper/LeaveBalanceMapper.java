package com.logitics.erp.leavebalance.mapper;

import com.logitics.erp.attendance.dto.AttendBasicInfoResponse;

public interface LeaveBalanceMapper {
    public AttendBasicInfoResponse getBasicInfo(Long employeeId);
}
