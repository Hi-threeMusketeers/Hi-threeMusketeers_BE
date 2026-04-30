package com.example.gamification.domain.course;

import com.example.gamification.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "user_course",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_course_member_course",
                        columnNames = {"member_id", "course_id"}
                )
        }
)
@NoArgsConstructor
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_course_id")
    private Long memberCourseId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static UserCourse create(Member member, Course course) {
        UserCourse userCourse = new UserCourse();
        userCourse.member = member;
        userCourse.course = course;
        userCourse.createdAt = LocalDateTime.now();
        userCourse.updatedAt = LocalDateTime.now();
        return userCourse;
    }
}