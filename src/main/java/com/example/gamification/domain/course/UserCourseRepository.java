package com.example.gamification.domain.course;

import com.example.gamification.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    boolean existsByMemberAndCourse(Member member, Course course);
    List<UserCourse> findByMember(Member member);
}
