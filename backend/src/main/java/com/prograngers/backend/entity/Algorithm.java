package com.prograngers.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Algorithm {
    @Id @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private Algorithms name;
}
