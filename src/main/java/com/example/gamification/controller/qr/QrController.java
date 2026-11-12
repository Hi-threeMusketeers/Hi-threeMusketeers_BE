
package com.example.gamification.controller.qr;

import com.example.gamification.dto.qr.QrVerifyRequest;
import com.example.gamification.dto.qr.QrVerifyResponse;
import com.example.gamification.service.qr.QrService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/qr")
public class QrController {

    private final QrService qrService;

    @PostMapping("/verify")
    public QrVerifyResponse verify(
            Authentication authentication,
            @Valid @RequestBody QrVerifyRequest request
    ) {
        String loginId = authentication.getName();

        return qrService.verify(loginId, request);
    }
}
