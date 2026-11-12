
package com.example.gamification.controller.pet;

import com.example.gamification.dto.pet.PetResponse;
import com.example.gamification.service.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    // 내 펫 정보 조회
    @GetMapping("/me")
    public PetResponse getMyPet(Authentication authentication) {

        String loginId = authentication.getName();

        return petService.getMyPet(loginId);
    }

    // 테스트용 경험치 +100
    @PostMapping("/test/exp")
    public PetResponse addTestExp(Authentication authentication) {

        String loginId = authentication.getName();

        return petService.addTestExp(loginId);
    }

    // 테스트용 레벨 및 경험치 초기화
    @PostMapping("/test/reset")
    public PetResponse resetTestExp(Authentication authentication) {

        String loginId = authentication.getName();

        return petService.resetTestExp(loginId);
    }
}
