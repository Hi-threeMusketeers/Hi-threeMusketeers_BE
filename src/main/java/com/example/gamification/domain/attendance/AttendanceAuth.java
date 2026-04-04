package com.example.gamification.domain.attendance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "attendance_auth")
@NoArgsConstructor
public class AttendanceAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "school_name", nullable = false, length = 100)
    private String schoolName;

    @Column(name = "school_latitude", nullable = false)
    private Double schoolLatitude;

    @Column(name = "school_longitude", nullable = false)
    private Double schoolLongitude;

    @Column(name = "radius", nullable = false)
    private Integer radius;

    @Column(name = "wifi_name", nullable = false, length = 100)
    private String wifiName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}