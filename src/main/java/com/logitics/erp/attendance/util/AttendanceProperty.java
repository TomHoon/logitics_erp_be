package com.logitics.erp.attendance.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@ConfigurationProperties(prefix = "attendance")
@Data
@Component
public class AttendanceProperty {

    private LocalTime startTime;
    private LocalTime endTime;
    private int lateThresholdMinutes;

}
