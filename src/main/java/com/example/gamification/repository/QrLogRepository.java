
package com.example.gamification.repository;

import com.example.gamification.domain.qr.QrLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface QrLogRepository extends JpaRepository<QrLog, Long> {

    boolean existsByMember_MemberIdAndAuthDate(
            Long memberId,
            LocalDate authDate
    );
}
