package com.example.gamification.service.course;

import com.example.gamification.domain.course.Course;
import com.example.gamification.domain.course.CourseRepository;
import com.example.gamification.domain.course.CourseSchedule;
import com.example.gamification.dto.course.CourseSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    public List<CourseSearchResponse> searchCourses(String keyword) {
        List<Course> courses = courseRepository.findByCourseNameContaining(keyword);

        return courses.stream()
                .map(course -> {
                    List<CourseSchedule> schedules = course.getCourseSchedules().stream()
                            .sorted(Comparator.comparing(CourseSchedule::getDayOfWeek)
                                    .thenComparing(CourseSchedule::getStartTime))
                            .toList();

                    String classroomText = schedules.stream()
                            .map(CourseSchedule::getClassroom)
                            .distinct()
                            .collect(Collectors.joining(", "));

                    String scheduleText = schedules.stream()
                            .map(schedule -> schedule.getDayOfWeek() + " "
                                    + schedule.getStartTime() + "-" + schedule.getEndTime())
                            .collect(Collectors.joining(", "));

                    return new CourseSearchResponse(
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getProfessorName(),
                            classroomText,
                            scheduleText
                    );
                })
                .toList();
    }
}
