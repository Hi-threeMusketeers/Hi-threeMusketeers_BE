package com.example.gamification.service.member;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.domain.pet.PetType;
import com.example.gamification.dto.member.CheckLoginIdResponse;
import com.example.gamification.dto.member.LoginRequest;
import com.example.gamification.dto.member.LoginResponse;
import com.example.gamification.dto.member.SignUpRequest;
import com.example.gamification.dto.member.SignUpResponse;
import com.example.gamification.jwt.JwtTokenProvider;
import com.example.gamification.repository.MemberRepository;
import com.example.gamification.repository.PetRepository;
import com.example.gamification.repository.PetTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private static final Long DEFAULT_PET_TYPE_ID = 1L;

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public CheckLoginIdResponse checkLoginId(String loginId) {
        boolean exists = memberRepository.existsByLoginId(loginId);

        if (exists) {
            return new CheckLoginIdResponse(false, "이미 존재하는 아이디입니다.");
        }

        return new CheckLoginIdResponse(true, "사용 가능한 아이디입니다.");
    }

    public SignUpResponse signUp(SignUpRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        PetType petType = petTypeRepository.findById(DEFAULT_PET_TYPE_ID)
                .orElseThrow(() -> new EntityNotFoundException("기본 펫 타입이 존재하지 않습니다."));

        Pet pet = Pet.create(
                petType,
                request.getPetNickname()
        );

        Member member = Member.create(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                pet
        );

        pet.setMember(member);

        Member savedMember = memberRepository.save(member);
        Pet savedPet = petRepository.save(pet);

        String accessToken = jwtTokenProvider.createToken(savedMember.getLoginId());

        return new SignUpResponse(
                savedMember.getMemberId(),
                savedMember.getLoginId(),
                savedPet.getName(),
                "회원가입이 완료되었습니다.",
                accessToken
        );
    }

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(member.getLoginId());

        return new LoginResponse(
                member.getMemberId(),
                member.getLoginId(),
                member.getPet().getName(),
                "로그인 성공",
                accessToken
        );
    }
}