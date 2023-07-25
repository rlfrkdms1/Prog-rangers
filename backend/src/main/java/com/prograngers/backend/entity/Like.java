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
public class Like {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="id")
    private Solution solution;

    @ManyToOne
    @JoinColumn(name="id")
    private Member member;

}
