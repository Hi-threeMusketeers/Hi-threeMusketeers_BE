package com.example.gamification.dto.todo;

public class CalendarTodoResponse {

    private Long todoId;
    private String title;
    private String content;
    private Boolean isCompleted;

    public CalendarTodoResponse(Long todoId, String title, String content, Boolean isCompleted) {
        this.todoId = todoId;
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
    }

    public Long getTodoId() {
        return todoId;
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