package com.example.gamification.controller;

import com.example.gamification.dto.todo.TodoCreateRequest;
import com.example.gamification.dto.todo.TodoResponse;
import com.example.gamification.service.TodoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoCreateRequest request, Authentication authentication) {
        String loginId = authentication.getName();
        return todoService.createTodo(loginId, request);
    }

    @DeleteMapping("/{todoId}")
    public String deleteTodo(@PathVariable Long todoId, Authentication authentication) {
        String loginId = authentication.getName();
        todoService.deleteTodo(loginId, todoId);
        return "투두가 삭제되었습니다.";
    }

    @PatchMapping("/{todoId}/complete")
    public TodoResponse completeTodo(@PathVariable Long todoId, Authentication authentication) {
        String loginId = authentication.getName();
        return todoService.completeTodo(loginId, todoId);
    }
}