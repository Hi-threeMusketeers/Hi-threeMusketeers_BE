
package com.example.gamification.service.qr;

import com.example.gamification.domain.member.Member;
import com.example.gamification.domain.pet.Pet;
import com.example.gamification.domain.qr.QrAuth;
import com.example.gamification.domain.qr.QrLog;
import com.example.gamification.dto.qr.QrVerifyRequest;
import com.example.gamification.dto.qr.QrVerifyResponse;
import com.example.gamification.repository.MemberRepository;
import com.example.gamification.repository.QrAuthRepository;
import com.example.gamification.repository.QrLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QrService {

    private static final int QR_EXP = 100;

    private final QrAuthRepository qrAuthRepository;
    private final QrLogRepository qrLogRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public QrVerifyResponse verify(String loginId, QrVerifyRequest request) {

        // 1. 로그인한 회원 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new EntityNotFoundException("해당 회원이 존재하지 않습니다.")
                );

        // 2. QR 인증 값 확인
        QrAuth qrAuth = qrAuthRepository.findByQrValue(request.getQrValue())
                .orElseThrow(() ->
                        new IllegalArgumentException("유효하지 않은 QR 코드입니다.")
                );

        // 3. 당일 중복 인증 검사
        // 프론트 테스트를 위해 임시 비활성화
        /*
        LocalDate today = LocalDate.now();

        boolean alreadyVerified =
                qrLogRepository.existsByMember_MemberIdAndAuthDate(
                        member.getMemberId(),
                        today
                );

        if (alreadyVerified) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "오늘은 이미 열람실 인증을 완료했습니다."
            );
        }
        */

        // 4. 회원의 펫 확인
        Pet pet = member.getPet();

        if (pet == null) {
            throw new EntityNotFoundException("회원에게 연결된 펫이 없습니다.");
        }

        // 5. QR 인증 기록 저장
        QrLog qrLog = new QrLog(member, qrAuth);
        qrLogRepository.save(qrLog);

        // 6. 펫 경험치 100 지급
        pet.addExp(QR_EXP);

        // 7. 결과 반환
        return new QrVerifyResponse(
                "SUCCESS",
                "열람실 인증 완료",
                QR_EXP,
                pet.getLevel(),
                pet.getExp()
        );
    }
}
