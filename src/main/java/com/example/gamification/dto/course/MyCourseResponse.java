package com.example.gamification.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyCourseResponse {
    private Long courseId;
    private String courseName;
    private String professorName;
    private String classroomText;
    private String scheduleText;
}