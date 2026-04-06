package com.example.gamification.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private Long memberId;
    private String loginId;
    private String nickname;
    private String message;
    private String accessToken;
}