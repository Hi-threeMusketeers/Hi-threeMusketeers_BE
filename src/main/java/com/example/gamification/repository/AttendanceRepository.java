package com.example.gamification.repository;

import com.example.gamification.domain.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByMember_MemberIdAndCourseSchedule_CourseScheduleIdAndAttendanceDate(
            Long memberId,
            Long courseScheduleId,
            LocalDate date
    );
}