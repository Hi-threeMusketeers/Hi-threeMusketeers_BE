package com.example.gamification.domain.qr;

import com.example.gamification.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "qr_log",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_qr_log_member_date",
                        columnNames = {"member_id", "auth_date"}
                )
        }
)
@NoArgsConstructor
public class QrLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qr_log_id")
    private Long qrLogId;

    @Column(name = "auth_date", nullable = false)
    private LocalDate authDate;

    @Column(name = "auth_datetime", nullable = false)
    private LocalDateTime authDatetime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qr_id", nullable = false)
    private QrAuth qrAuth;
}