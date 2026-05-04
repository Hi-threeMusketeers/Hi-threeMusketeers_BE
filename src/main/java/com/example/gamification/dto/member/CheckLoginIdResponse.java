package com.example.gamification.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckLoginIdResponse {
    private boolean available;
    private String message;
}