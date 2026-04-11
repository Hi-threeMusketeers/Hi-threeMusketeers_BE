package com.example.gamification.controller.course;

import com.example.gamification.dto.course.CourseSearchResponse;
import com.example.gamification.dto.course.SaveUserCoursesRequest;
import com.example.gamification.dto.course.SaveUserCoursesResponse;
import com.example.gamification.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.gamification.dto.course.MyCourseResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/search")
    public List<CourseSearchResponse> searchCourses(@RequestParam String keyword) {
        return courseService.searchCourses(keyword);
    }

    @PostMapping("/me")
    public SaveUserCoursesResponse saveMyCourses(
            Authentication authentication,
            @Valid @RequestBody SaveUserCoursesRequest request
    ) {
        return courseService.saveUserCourses(authentication.getName(), request);
    }
    @GetMapping("/me")
    public List<MyCourseResponse> getMyCourses(Authentication authentication) {
        return courseService.getMyCourses(authentication.getName());
    }
}