package com.example.gamification.controller;

import com.example.gamification.dto.todo.CalendarDateResponse;
import com.example.gamification.dto.todo.CalendarMonthResponse;
import com.example.gamification.service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

    private final TodoService todoService;

    public CalendarController(TodoService todoService) {
        this.todoService = todoService;
    }

    // 특정 날짜의 투두 조회
    @GetMapping("/date")
    public CalendarDateResponse getTodosByDate(
            @RequestParam Long memberId,
            @RequestParam LocalDate date
    ) {
        return todoService.getTodosByDate(memberId, date);
    }

    // 특정 월의 투두가 있는 날짜만 조회
    @GetMapping("/month")
    public CalendarMonthResponse getMonthlyTodoStatus(
            @RequestParam Long memberId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return todoService.getMonthlyTodoStatus(memberId, year, month);
    }
}