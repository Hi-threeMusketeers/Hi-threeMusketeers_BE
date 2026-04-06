package com.example.gamification.service.member;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.member.MemberRepository;
import com.example.gamification.dto.member.LoginRequest;
import com.example.gamification.dto.member.LoginResponse;
import com.example.gamification.dto.member.SignUpRequest;
import com.example.gamification.dto.member.SignUpResponse;
import com.example.gamification.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignUpResponse signUp(SignUpRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = Member.create(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname()
        );

        Member savedMember = memberRepository.save(member);

        return new SignUpResponse(
                savedMember.getMemberId(),
                savedMember.getLoginId(),
                savedMember.getNickname(),
                "회원가입이 완료되었습니다."
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
                member.getNickname(),
                "로그인 성공",
                accessToken
        );
    }
}