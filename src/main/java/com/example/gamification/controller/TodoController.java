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
    public void deleteTodo(
            Authentication authentication,
            @PathVariable Long todoId
    ) {
        String loginId = authentication.getName();
        todoService.deleteTodo(loginId, todoId);
    }
    @PatchMapping("/{todoId}/toggle")
    public TodoResponse completeTodo(@PathVariable Long todoId, Authentication authentication) {
        String loginId = authentication.getName();
        return todoService.completeTodo(loginId, todoId);
    }
}