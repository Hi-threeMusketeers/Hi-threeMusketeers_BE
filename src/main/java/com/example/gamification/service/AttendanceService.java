package com.example.gamification.service;

import com.example.gamification.domain.attendance.Attendance;
import com.example.gamification.domain.attendance.AttendanceAuth;
import com.example.gamification.domain.course.CourseSchedule;
import com.example.gamification.domain.member.Member;
import com.example.gamification.dto.attendance.AttendanceRequest;
import com.example.gamification.dto.attendance.AttendanceResponse;
import com.example.gamification.repository.AttendanceAuthRepository;
import com.example.gamification.repository.AttendanceRepository;
import com.example.gamification.repository.CourseScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceAuthRepository attendanceAuthRepository;
    private final CourseScheduleRepository courseScheduleRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            AttendanceAuthRepository attendanceAuthRepository,
            CourseScheduleRepository courseScheduleRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceAuthRepository = attendanceAuthRepository;
        this.courseScheduleRepository = courseScheduleRepository;
    }

    public AttendanceResponse checkAttendance(Member member, AttendanceRequest request) {

        // 1. 현재 요일/시간
        String today = convertDayOfWeek(LocalDate.now().getDayOfWeek().name());
        LocalTime now = LocalTime.now();

        // 2. 현재 수업 찾기
        CourseSchedule courseSchedule = courseScheduleRepository
                .findCurrentSchedule(member.getMemberId(), today, now)
                .orElseThrow(() -> new IllegalStateException("현재 출석 가능한 수업이 없습니다."));

        // 3. 출석 인증 정보
        AttendanceAuth auth = attendanceAuthRepository.findFirstBySchoolName("홍익대학교")
                .orElseThrow(() -> new EntityNotFoundException("출석 인증 정보 없음"));

        // 4. 중복 체크
        if (attendanceRepository.existsByMember_MemberIdAndCourseSchedule_CourseScheduleIdAndAttendanceDate(
                member.getMemberId(),
                courseSchedule.getCourseScheduleId(),
                LocalDate.now()
        )) {
            throw new IllegalStateException("이미 출석한 수업입니다.");
        }

        // 5. 거리 체크
        double distance = calculateDistance(
                request.getLatitude(),
                request.getLongitude(),
                auth.getSchoolLatitude(),
                auth.getSchoolLongitude()
        );

        if (distance > auth.getRadius()) {
            throw new IllegalArgumentException("학교 반경 밖입니다.");
        }

        // 6. 와이파이 체크
        if (!auth.getWifiName().equalsIgnoreCase(request.getWifiName())) {
            throw new IllegalArgumentException("학교 와이파이가 아닙니다.");
        }

        // 7. 저장
        Attendance attendance = new Attendance(member, courseSchedule, auth);
        Attendance saved = attendanceRepository.save(attendance);

        return new AttendanceResponse(
                "SUCCESS",
                "출석 완료",
                saved.getAttendanceId(),
                saved.getAttendanceDate(),
                saved.getAttendanceDatetime()
        );
    }

    // 🔥 요일 변환 (MONDAY → 월)
    private String convertDayOfWeek(String day) {
        return switch (day) {
            case "MONDAY" -> "월";
            case "TUESDAY" -> "화";
            case "WEDNESDAY" -> "수";
            case "THURSDAY" -> "목";
            case "FRIDAY" -> "금";
            case "SATURDAY" -> "토";
            case "SUNDAY" -> "일";
            default -> throw new IllegalArgumentException("요일 변환 실패");
        };
    }

    // 거리 계산
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371000;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2)
                        * Math.sin(dLon / 2);

        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}