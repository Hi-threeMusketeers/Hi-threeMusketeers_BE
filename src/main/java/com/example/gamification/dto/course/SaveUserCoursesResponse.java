package com.example.gamification.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveUserCoursesResponse {
    private int savedCount;
    private String message;
}