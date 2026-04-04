package com.example.gamification.domain.member;

import com.example.gamification.domain.attendance.Attendance;
import com.example.gamification.domain.course.UserCourse;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.domain.qr.QrLog;
import com.example.gamification.domain.todo.Todo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "login_id", nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "last_attendance_date")
    private LocalDate lastAttendanceDate;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member")
    private List<Todo> todos;

    @OneToOne(mappedBy = "member")
    private Pet pet;

    @OneToMany(mappedBy = "member")
    private List<UserCourse> userCourses;

    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "member")
    private List<QrLog> qrLogs;
}