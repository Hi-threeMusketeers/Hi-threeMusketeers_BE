package com.example.gamification.domain.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // 🔥 여기 핵심
    @Column(name = "pet_nickname", nullable = false, length = 20)
    private String petNickname;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Member create(String loginId, String password, String petNickname) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.petNickname = petNickname;
        member.createdAt = LocalDateTime.now();
        member.updatedAt = LocalDateTime.now();
        return member;
    }
}