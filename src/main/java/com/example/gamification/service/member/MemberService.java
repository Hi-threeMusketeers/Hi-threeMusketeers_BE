package com.example.gamification.service.member;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.domain.pet.PetType;
import com.example.gamification.dto.member.*;
import com.example.gamification.jwt.JwtTokenProvider;
import com.example.gamification.repository.MemberRepository;
import com.example.gamification.repository.PetRepository;
import com.example.gamification.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gamification.dto.member.LogoutResponse;
import com.example.gamification.jwt.TokenBlacklistService;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository; // 🔥 추가
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    public CheckLoginIdResponse checkLoginId(String loginId) {
        boolean exists = memberRepository.existsByLoginId(loginId);

        if (exists) {
            return new CheckLoginIdResponse(false, "이미 존재하는 아이디입니다.");
        }

        return new CheckLoginIdResponse(true, "사용 가능한 아이디입니다.");
    }

    public SignUpResponse signUp(SignUpRequest request) {

        // 1. Member 생성
        Member member = Member.create(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword())
        );
        memberRepository.save(member);

        // 2. PetType 가져오기 (🔥 핵심)
        PetType petType = petTypeRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("펫 타입 없음"));

        // 3. Pet 생성
        Pet pet = new Pet(
                request.getPetName(),
                member,
                petType
        );
        petRepository.save(pet);

        String accessToken =
                jwtTokenProvider.createToken(member.getLoginId());

        return new SignUpResponse(
                member.getMemberId(),
                member.getLoginId(),
                pet.getPetId(),
                "회원가입 완료",
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
                member.getPet().getPetId(),
                "로그인 성공",
                accessToken
        );
    }
    public LogoutResponse logout(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("토큰이 없습니다.");
        }

        String token = authorizationHeader.substring(7);
        tokenBlacklistService.add(token);

        return new LogoutResponse("로그아웃이 완료되었습니다.");
    }
}