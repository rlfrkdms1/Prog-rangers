package com.prograngers.backend.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "solution")
    private List<Like> likes = new ArrayList<>();

    private Integer scraps;

    @OneToOne
    @JoinColumn(name="id")
    @Nullable
    private Solution scrapId;

    private LocalDate date;

    @OneToOne
    @JoinColumn(name="id")
    private Algorithm algorithm;

    @OneToOne
    @JoinColumn(name="id")
    private DataStructure dataStructure;

    @Enumerated(EnumType.STRING)
    private Level level;

}
