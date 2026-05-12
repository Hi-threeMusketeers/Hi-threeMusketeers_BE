package com.example.gamification.domain.pet;

import com.example.gamification.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "pet")
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_type_id", nullable = false)
    private PetType petType;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "exp", nullable = false)
    private Integer exp;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Pet create(PetType petType, String name) {
        Pet pet = new Pet();
        pet.petType = petType;
        pet.name = name;
        pet.level = 1;
        pet.exp = 0;
        pet.createdAt = LocalDateTime.now();
        pet.updatedAt = LocalDateTime.now();
        return pet;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_type_id", nullable = false)
    private PetType petType;

    // 🔥 생성자 수정
    public Pet(String name, Member member, PetType petType) {
        this.name = name;
        this.member = member;
        this.petType = petType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}