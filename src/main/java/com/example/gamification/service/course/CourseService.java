package com.example.gamification.service.course;

import com.example.gamification.domain.course.Course;
import com.example.gamification.repository.CourseRepository;
import com.example.gamification.domain.course.CourseSchedule;
import com.example.gamification.domain.course.UserCourse;
import com.example.gamification.repository.UserCourseRepository;
import com.example.gamification.domain.member.Member;
import com.example.gamification.dto.course.CourseSearchResponse;
import com.example.gamification.dto.course.SaveUserCoursesRequest;
import com.example.gamification.dto.course.SaveUserCoursesResponse;
import com.example.gamification.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gamification.dto.course.MyCourseResponse;
import com.example.gamification.dto.course.CurrentCourseResponse;
import java.time.DayOfWeek;
import java.time.LocalTime;
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

    // 강의 검색
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

    // 시간표 저장
    @Transactional
    public SaveUserCoursesResponse saveUserCourses(
            String loginId,
            SaveUserCoursesRequest request
    ) {

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

        return new SaveUserCoursesResponse(
                savedCount,
                "시간표 저장이 완료되었습니다."
        );
    }

    // 내 시간표 조회
    public List<MyCourseResponse> getMyCourses(String loginId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<UserCourse> userCourses = userCourseRepository.findByMember(member);

        return userCourses.stream()
                .map(UserCourse::getCourse)
                .distinct()
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

                    return new MyCourseResponse(
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getProfessorName(),
                            classroomText,
                            scheduleText
                    );
                })
                .toList();
    }

    // 시간표 삭제
    @Transactional
    public void deleteMyCourse(String loginId, Long courseId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 과목입니다."));

        userCourseRepository.deleteByMemberAndCourse(member, course);
    }

    public CurrentCourseResponse getCurrentCourse(String loginId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        List<UserCourse> userCourses = userCourseRepository.findByMember(member);

        java.time.ZonedDateTime now =
                java.time.ZonedDateTime.now(
                        java.time.ZoneId.of("Asia/Seoul")
                );

        DayOfWeek nowDay = now.getDayOfWeek();
        LocalTime nowTime = now.toLocalTime();

        String today;

        switch (nowDay) {
            case MONDAY -> today = "월";
            case TUESDAY -> today = "화";
            case WEDNESDAY -> today = "수";
            case THURSDAY -> today = "목";
            case FRIDAY -> today = "금";
            case SATURDAY -> today = "토";
            case SUNDAY -> today = "일";
            default -> {
                return new CurrentCourseResponse("지금은 쉬는 시간이에요!");
            }
        }

        for (UserCourse userCourse : userCourses) {

            Course course = userCourse.getCourse();

            for (CourseSchedule schedule : course.getCourseSchedules()) {

                boolean isToday = schedule.getDayOfWeek().equals(today);

                boolean isNow =
                        !nowTime.isBefore(schedule.getStartTime()) &&
                                !nowTime.isAfter(schedule.getEndTime());

                if (isToday && isNow) {

                    return new CurrentCourseResponse(
                            "지금은 " + course.getCourseName() + " 시간이네요!"
                    );
                }
            }
        }

        return new CurrentCourseResponse("지금은 쉬는 시간이에요!");
    }
}
