package com.example.gamification.controller;

import com.example.gamification.dto.attendance.AttendanceRequest;
import com.example.gamification.dto.attendance.AttendanceResponse;
import com.example.gamification.service.AttendanceService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public AttendanceResponse checkAttendance(
            Authentication authentication,
            @RequestBody AttendanceRequest request
    ) {
        String loginId = authentication.getName();
        return attendanceService.checkAttendance(loginId, request);
    }
}