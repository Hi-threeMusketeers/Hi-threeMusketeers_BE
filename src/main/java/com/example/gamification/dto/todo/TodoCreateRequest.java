package com.example.gamification.dto.todo;

import java.time.LocalDate;

public class TodoCreateRequest {

    private Long memberId;
    private LocalDate todoDate;
    private String title;
    private String content;

    public TodoCreateRequest() {
    }

    public Long getMemberId() {
        return memberId;
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
}