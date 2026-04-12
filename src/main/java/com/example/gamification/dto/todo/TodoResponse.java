package com.example.gamification.dto.todo;

import java.time.LocalDate;

public class TodoResponse {

    private Long todoId;
    private LocalDate todoDate;
    private String title;
    private String content;
    private Boolean isCompleted;

    public TodoResponse(Long todoId, LocalDate todoDate, String title, String content, Boolean isCompleted) {
        this.todoId = todoId;
        this.todoDate = todoDate;
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
    }

    public Long getTodoId() {
        return todoId;
    }

    public LocalDate getTodoDate() {
        return todoDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }
}