package com.example.gamification.dto.todo;

import java.time.LocalDate;
import java.util.List;

public class CalendarDateResponse {

    private LocalDate date;
    private List<CalendarTodoResponse> todos;

    public CalendarDateResponse(LocalDate date, List<CalendarTodoResponse> todos) {
        this.date = date;
        this.todos = todos;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<CalendarTodoResponse> getTodos() {
        return todos;
    }
}