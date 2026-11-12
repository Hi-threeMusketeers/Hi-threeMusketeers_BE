package com.example.gamification.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PetResponse {

    private Long petId;
    private String name;
    private Integer level;
    private Integer exp;
}