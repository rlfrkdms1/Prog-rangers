package com.prograngers.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataStructure {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private DataStructures name;

}
