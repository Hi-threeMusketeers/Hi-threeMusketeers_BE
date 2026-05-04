package com.example.gamification.dto.course;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SaveUserCoursesRequest {

    @NotEmpty(message = "선택한 과목이 없습니다.")
    private List<Long> courseIds;
}
