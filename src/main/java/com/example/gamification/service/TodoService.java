package com.example.gamification.service;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.todo.Todo;
import com.example.gamification.dto.todo.CalendarDateResponse;
import com.example.gamification.dto.todo.CalendarMonthResponse;
import com.example.gamification.dto.todo.CalendarTodoResponse;
import com.example.gamification.dto.todo.TodoCreateRequest;
import com.example.gamification.dto.todo.TodoResponse;
import com.example.gamification.repository.MemberRepository;
import com.example.gamification.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public TodoService(TodoRepository todoRepository, MemberRepository memberRepository) {
        this.todoRepository = todoRepository;
        this.memberRepository = memberRepository;
    }

    // 투두 생성
    public TodoResponse createTodo(TodoCreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("해당 회원이 존재하지 않습니다."));

        Todo todo = new Todo(
                member,
                request.getTodoDate(),
                request.getTitle(),
                request.getContent()
        );

        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(
                savedTodo.getTodoId(),
                savedTodo.getMember().getMemberId(),
                savedTodo.getTodoDate(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                savedTodo.getIsCompleted()
        );
    }

    // 투두 완료 처리
    public TodoResponse completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 존재하지 않습니다."));

        todo.complete();

        Todo updatedTodo = todoRepository.save(todo);

        return new TodoResponse(
                updatedTodo.getTodoId(),
                updatedTodo.getMember().getMemberId(),
                updatedTodo.getTodoDate(),
                updatedTodo.getTitle(),
                updatedTodo.getContent(),
                updatedTodo.getIsCompleted()
        );
    }

    // 투두 삭제
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 존재하지 않습니다."));

        todoRepository.delete(todo);
    }

    // 특정 날짜의 투두 조회
    public CalendarDateResponse getTodosByDate(Long memberId, LocalDate date) {
        List<Todo> todos = todoRepository.findByMember_MemberIdAndTodoDate(memberId, date);

        List<CalendarTodoResponse> todoResponses = todos.stream()
                .map(todo -> new CalendarTodoResponse(
                        todo.getTodoId(),
                        todo.getTitle(),
                        todo.getContent(),
                        todo.getIsCompleted()
                ))
                .toList();

        return new CalendarDateResponse(date, todoResponses);
    }

    // 특정 월의 투두가 있는 날짜만 조회
    public CalendarMonthResponse getMonthlyTodoStatus(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Todo> todos = todoRepository.findByMember_MemberIdAndTodoDateBetween(memberId, startDate, endDate);

        List<LocalDate> dates = todos.stream()
                .map(Todo::getTodoDate)
                .distinct()
                .toList();

        return new CalendarMonthResponse(year, month, dates);
    }
}