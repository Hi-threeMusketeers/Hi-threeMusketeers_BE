package com.example.gamification.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseSearchResponse {

    private Long courseId;
    private String courseName;
    private String professorName;

    private String classroom;
    private String dayOfWeek;
    private Integer startPeriod;
    private Integer endPeriod;

    private String scheduleText;
}