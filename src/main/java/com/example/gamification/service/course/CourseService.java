package com.example.gamification.service.course;

import com.example.gamification.domain.course.Course;
import com.example.gamification.domain.course.CourseRepository;
import com.example.gamification.domain.course.CourseSchedule;
import com.example.gamification.domain.course.UserCourse;
import com.example.gamification.domain.course.UserCourseRepository;
import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.member.MemberRepository;
import com.example.gamification.dto.course.CourseSearchResponse;
import com.example.gamification.dto.course.SaveUserCoursesRequest;
import com.example.gamification.dto.course.SaveUserCoursesResponse;
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
    private final UserCourseRepository userCourseRepository;
    private final MemberRepository memberRepository;

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

    @Transactional
    public SaveUserCoursesResponse saveUserCourses(String loginId, SaveUserCoursesRequest request) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<Course> courses = courseRepository.findAllById(request.getCourseIds());

        if (courses.isEmpty()) {
            throw new IllegalArgumentException("선택한 과목이 존재하지 않습니다.");
        }

        int savedCount = 0;

        for (Course course : courses) {
            boolean exists = userCourseRepository.existsByMemberAndCourse(member, course);

            if (!exists) {
                UserCourse userCourse = UserCourse.create(member, course);
                userCourseRepository.save(userCourse);
                savedCount++;
            }
        }

        return new SaveUserCoursesResponse(savedCount, "시간표 저장이 완료되었습니다.");
    }
}
