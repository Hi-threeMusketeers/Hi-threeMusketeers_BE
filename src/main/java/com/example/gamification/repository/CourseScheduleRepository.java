package com.example.gamification.repository;

import com.example.gamification.domain.course.CourseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface CourseScheduleRepository extends JpaRepository<CourseSchedule, Long> {

    @Query("""
        SELECT cs
        FROM UserCourse uc
        JOIN uc.course c
        JOIN c.courseSchedules cs
        WHERE uc.member.memberId = :memberId
          AND cs.dayOfWeek = :dayOfWeek
          AND cs.startTime <= :now
          AND cs.endTime >= :now
    """)
    Optional<CourseSchedule> findCurrentSchedule(
            @Param("memberId") Long memberId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("now") LocalTime now
    );
}