package com.example.gamification.dto.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceResponse {

    private String status;
    private String message;
    private Long attendanceId;
    private LocalDate attendanceDate;
    private LocalDateTime attendanceDatetime;

    private Integer gainedExp;
    private Integer petLevel;
    private Integer petExp;

    public AttendanceResponse(
            String status,
            String message,
            Long attendanceId,
            LocalDate attendanceDate,
            LocalDateTime attendanceDatetime,
            Integer gainedExp,
            Integer petLevel,
            Integer petExp
    ) {
        this.status = status;
        this.message = message;
        this.attendanceId = attendanceId;
        this.attendanceDate = attendanceDate;
        this.attendanceDatetime = attendanceDatetime;
        this.gainedExp = gainedExp;
        this.petLevel = petLevel;
        this.petExp = petExp;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalDateTime getAttendanceDatetime() {
        return attendanceDatetime;
    }

    public Integer getGainedExp() {
        return gainedExp;
    }

    public Integer getPetLevel() {
        return petLevel;
    }

    public Integer getPetExp() {
        return petExp;
    }
}