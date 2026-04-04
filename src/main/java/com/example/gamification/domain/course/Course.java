package com.example.gamification.domain.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "course")
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_name", nullable = false, length = 50)
    private String courseName;

    @Column(name = "course_code", nullable = false, length = 50)
    private String courseCode;

    @Column(name = "professor_name", length = 50)
    private String professorName;

    @OneToMany(mappedBy = "course")
    private List<CourseSchedule> courseSchedules;

    @OneToMany(mappedBy = "course")
    private List<UserCourse> userCourses;
}