
package com.example.gamification.dto.qr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QrVerifyResponse {

    private String status;
    private String message;
    private Integer earnedExp;
    private Integer petLevel;
    private Integer petExp;
}
