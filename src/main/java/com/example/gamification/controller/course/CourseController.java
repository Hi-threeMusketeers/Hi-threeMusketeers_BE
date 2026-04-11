package com.example.gamification.controller.course;

import com.example.gamification.dto.course.CourseSearchResponse;
import com.example.gamification.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}