package com.example.gamification.repository;

import com.example.gamification.domain.pet.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}