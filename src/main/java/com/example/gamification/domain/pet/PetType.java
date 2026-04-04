package com.example.gamification.domain.pet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "pet_type")
@NoArgsConstructor
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_type_id")
    private Long petTypeId;

    @Column(name = "type_name", nullable = false, unique = true, length = 20)
    private String typeName;

    @Column(name = "model_url", length = 255)
    private String modelUrl;

    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailUrl;
}