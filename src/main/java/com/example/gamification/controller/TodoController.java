package com.example.gamification.controller;

import com.example.gamification.dto.todo.TodoCreateRequest;
import com.example.gamification.dto.todo.TodoResponse;
import com.example.gamification.service.TodoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //투두 저장
    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoCreateRequest request) {
        return todoService.createTodo(request);
    }

    //투두 삭제
    @DeleteMapping("/{todoId}")
    public String deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return "투두가 삭제되었습니다.";
    }

    //투두 완료 처리
    @PatchMapping("/{todoId}/complete")
    public TodoResponse completeTodo(@PathVariable Long todoId) {
        return todoService.completeTodo(todoId);
    }
}