package com.example.gamification.service;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.todo.Todo;
import com.example.gamification.dto.todo.*;
import com.example.gamification.repository.MemberRepository;
import com.example.gamification.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public TodoService(TodoRepository todoRepository, MemberRepository memberRepository) {
        this.todoRepository = todoRepository;
        this.memberRepository = memberRepository;
    }

    // 투두 생성
    public TodoResponse createTodo(String loginId, TodoCreateRequest request) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));

        Todo todo = new Todo(
                member,
                request.getTodoDate(),
                request.getTitle(),
                request.getContent()
        );

        Todo saved = todoRepository.save(todo);

        return new TodoResponse(
                saved.getTodoId(),
                saved.getTodoDate(),
                saved.getTitle(),
                saved.getContent(),
                saved.getIsCompleted()
        );
    }

    // 완료 토글
    public TodoResponse completeTodo(String loginId, Long todoId) {
        Todo todo = todoRepository.findByTodoIdAndMember_LoginId(todoId, loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 없거나 권한이 없습니다."));

        todo.toggleComplete();

        return new TodoResponse(
                todo.getTodoId(),
                todo.getTodoDate(),
                todo.getTitle(),
                todo.getContent(),
                todo.getIsCompleted()
        );
    }

    // 삭제
    public void deleteTodo(String loginId, Long todoId) {
        Todo todo = todoRepository.findByTodoIdAndMember_LoginId(todoId, loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 없거나 권한이 없습니다."));

        todoRepository.delete(todo);
    }

    // 날짜 조회
    public CalendarDateResponse getTodosByDate(String loginId, LocalDate date) {
        List<Todo> todos = todoRepository.findByMember_LoginIdAndTodoDate(loginId, date);

        List<CalendarTodoResponse> result = todos.stream()
                .map(todo -> new CalendarTodoResponse(
                        todo.getTodoId(),
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getIsCompleted()
                ))
                .toList();

        return new CalendarDateResponse(date, result);
    }

    // 월 조회
    public CalendarMonthResponse getMonthlyTodoStatus(String loginId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Todo> todos = todoRepository
                .findByMember_LoginIdAndTodoDateBetween(loginId, start, end);

        List<LocalDate> dates = todos.stream()
                .map(Todo::getTodoDate)
                .distinct()
                .toList();

        return new CalendarMonthResponse(year, month, dates);
    }
}