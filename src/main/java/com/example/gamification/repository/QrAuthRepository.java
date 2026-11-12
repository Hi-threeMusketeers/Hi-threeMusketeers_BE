
package com.example.gamification.repository;

import com.example.gamification.domain.qr.QrAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QrAuthRepository extends JpaRepository<QrAuth, Long> {

    Optional<QrAuth> findByQrValue(String qrValue);
}
