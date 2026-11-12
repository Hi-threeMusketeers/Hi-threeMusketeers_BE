
package com.example.gamification.dto.qr;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QrVerifyRequest {

    @NotBlank(message = "QR 인증 값은 필수입니다.")
    private String qrValue;
}
