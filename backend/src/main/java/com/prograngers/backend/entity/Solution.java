package com.prograngers.backend.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="id")
    private Problem problem;

    @ManyToOne
    @JoinColumn(name="id")
    private Member member;

    private String title;

    private boolean pubilc;

    private String code;

    private String description;

    private Like likes;

    private Integer scraps;

    @OneToOne
    @JoinColumn(name="id")
    @Nullable
    private Solution scrapId;

    private LocalDate date;

}
