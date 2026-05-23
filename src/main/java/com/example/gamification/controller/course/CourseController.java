package com.example.gamification.controller.course;

import com.example.gamification.dto.course.CourseSearchResponse;
import com.example.gamification.dto.course.MyCourseResponse;
import com.example.gamification.dto.course.SaveUserCoursesRequest;
import com.example.gamification.dto.course.SaveUserCoursesResponse;
import com.example.gamification.service.course.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.gamification.dto.course.CurrentCourseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    // 강의 검색
    @GetMapping("/search")
    public List<CourseSearchResponse> searchCourses(
            @RequestParam String keyword
    ) {
        return courseService.searchCourses(keyword);
    }

    // 시간표 저장
    @PostMapping("/me")
    public SaveUserCoursesResponse saveMyCourses(
            Authentication authentication,
            @Valid @RequestBody SaveUserCoursesRequest request
    ) {

        return courseService.saveUserCourses(
                authentication.getName(),
                request
        );
    }

    // 내 시간표 조회
    @GetMapping("/me")
    public List<MyCourseResponse> getMyCourses(
            Authentication authentication
    ) {

        return courseService.getMyCourses(
                authentication.getName()
        );
    }

    //  시간표 삭제
    @DeleteMapping("/me/{courseId}")
    public String deleteMyCourse(
            Authentication authentication,
            @PathVariable Long courseId
    ) {

        courseService.deleteMyCourse(
                authentication.getName(),
                courseId
        );

        return "시간표 삭제 완료";
    }
    @GetMapping("/current")
    public CurrentCourseResponse getCurrentCourse(
            Authentication authentication
    ) {

        return courseService.getCurrentCourse(
                authentication.getName()
        );
    }
}