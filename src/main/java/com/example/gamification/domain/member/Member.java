package com.example.gamification.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.example.gamification.domain.todo.Todo;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.domain.course.UserCourse;
import com.example.gamification.domain.attendance.Attendance;
import com.example.gamification.domain.qr.QrLog;
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

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member")
    private List<Todo> todos;

    @OneToMany(mappedBy = "member")
    private List<UserCourse> userCourses;

    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "member")
    private List<QrLog> qrLogs;

    public static Member create(String loginId, String password) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.createdAt = LocalDateTime.now();
        member.updatedAt = LocalDateTime.now();
        return member;
    }
}