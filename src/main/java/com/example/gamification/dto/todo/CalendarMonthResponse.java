package com.example.gamification.dto.todo;

import java.time.LocalDate;
import java.util.List;

public class CalendarMonthResponse {

    private int year;
    private int month;
    private List<LocalDate> dates;

    public CalendarMonthResponse(int year, int month, List<LocalDate> dates) {
        this.year = year;
        this.month = month;
        this.dates = dates;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public List<LocalDate> getDates() {
        return dates;
    }
}