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

    public TodoResponse createTodo(String loginId, TodoCreateRequest request) {
        Member member = memberRepository.findByLoginId(loginId)
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
                savedTodo.getTodoDate(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                savedTodo.getIsCompleted()
        );
    }

    public TodoResponse completeTodo(String loginId, Long todoId) {
        Todo todo = todoRepository.findByTodoIdAndMember_LoginId(todoId, loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 존재하지 않거나 권한이 없습니다."));

        todo.complete();

        Todo updatedTodo = todoRepository.save(todo);

        return new TodoResponse(
                updatedTodo.getTodoId(),
                updatedTodo.getTodoDate(),
                updatedTodo.getTitle(),
                updatedTodo.getContent(),
                updatedTodo.getIsCompleted()
        );
    }

    public void deleteTodo(String loginId, Long todoId) {
        Todo todo = todoRepository.findByTodoIdAndMember_LoginId(todoId, loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 투두가 존재하지 않거나 권한이 없습니다."));

        todoRepository.delete(todo);
    }

    public CalendarDateResponse getTodosByDate(String loginId, LocalDate date) {
        List<Todo> todos = todoRepository.findByMember_LoginIdAndTodoDate(loginId, date);

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

    public CalendarMonthResponse getMonthlyTodoStatus(String loginId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Todo> todos = todoRepository.findByMember_LoginIdAndTodoDateBetween(loginId, startDate, endDate);

        List<LocalDate> dates = todos.stream()
                .map(Todo::getTodoDate)
                .distinct()
                .toList();

        return new CalendarMonthResponse(year, month, dates);
    }
}