package com.example.gamification.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {
    private Long memberId;
    private String loginId;
    private String nickname;
    private String message;
}
