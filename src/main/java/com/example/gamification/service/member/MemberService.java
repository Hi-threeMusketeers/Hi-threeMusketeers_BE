package com.example.gamification.service.member;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.member.MemberRepository;
import com.example.gamification.dto.member.SignUpRequest;
import com.example.gamification.dto.member.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponse signUp(SignUpRequest request) {

        // 아이디 중복 체크
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 닉네임 중복 체크
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // 회원 생성
        Member member = Member.create(
                request.getLoginId(),
                request.getPassword(),
                request.getNickname()
        );

        Member savedMember = memberRepository.save(member);

        return new SignUpResponse(
                savedMember.getMemberId(),
                savedMember.getLoginId(),
                savedMember.getNickname()
        );
    }
}