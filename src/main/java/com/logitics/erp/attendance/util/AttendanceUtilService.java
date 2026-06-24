package com.logitics.erp.attendance.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AttendanceUtilService {
    private final AttendanceProperty attendanceProperty;

    // 체크인 타임 확인
    public Boolean isLate(LocalTime checkInTime) {
        LocalTime now = LocalTime.now();

        long overMinutes = Duration
                .between(attendanceProperty.getStartTime(), checkInTime)
                .toMinutes();

        if (overMinutes >= attendanceProperty.getLateThresholdMinutes()) {
            return true;
        } else {
            return false;
        }

    }
}
