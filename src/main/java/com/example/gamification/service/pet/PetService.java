package com.example.gamification.service.pet;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.dto.pet.PetResponse;
import com.example.gamification.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final MemberRepository memberRepository;

    // 내 펫 정보 조회
    @Transactional(readOnly = true)
    public PetResponse getMyPet(String loginId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 회원이 존재하지 않습니다.")
                );

        Pet pet = member.getPet();

        if (pet == null) {
            throw new EntityNotFoundException("회원에게 연결된 펫이 없습니다.");
        }

        return new PetResponse(
                pet.getPetId(),
                pet.getName(),
                pet.getLevel(),
                pet.getExp()
        );
    }

    // 테스트용 경험치 +100
    @Transactional
    public PetResponse addTestExp(String loginId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 회원이 존재하지 않습니다.")
                );

        Pet pet = member.getPet();

        if (pet == null) {
            throw new EntityNotFoundException("회원에게 연결된 펫이 없습니다.");
        }

        pet.addExp(100);

        return new PetResponse(
                pet.getPetId(),
                pet.getName(),
                pet.getLevel(),
                pet.getExp()
        );
    }

    // 테스트용 레벨 및 경험치 초기화
    @Transactional
    public PetResponse resetTestExp(String loginId) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 회원이 존재하지 않습니다.")
                );

        Pet pet = member.getPet();

        if (pet == null) {
            throw new EntityNotFoundException("회원에게 연결된 펫이 없습니다.");
        }

        pet.resetLevelAndExp();

        return new PetResponse(
                pet.getPetId(),
                pet.getName(),
                pet.getLevel(),
                pet.getExp()
        );
    }
}