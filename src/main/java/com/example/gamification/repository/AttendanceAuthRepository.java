package com.example.gamification.repository;

import com.example.gamification.domain.attendance.AttendanceAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendanceAuthRepository extends JpaRepository<AttendanceAuth, Long> {

    Optional<AttendanceAuth> findFirstBySchoolName(String schoolName);
}