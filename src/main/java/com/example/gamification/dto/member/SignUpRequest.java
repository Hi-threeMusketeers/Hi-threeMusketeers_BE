package com.example.gamification.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(max = 20)
    private String loginId;

    @NotBlank
    @Size(max = 255)
    private String password;

    // 🔥 변경됨
    @NotBlank
    @Size(max = 20)
    private String petNickname;
}
