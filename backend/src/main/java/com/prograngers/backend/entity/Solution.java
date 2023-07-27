package com.prograngers.backend.entity;

import com.prograngers.backend.dto.SolutionRequest;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="problem_id")
    private Problem problem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="member_id")
    private Member member;

    private String title;

    private boolean pubilc;

    private String code;

    private String description;

    @OneToMany(mappedBy = "solution")
    private List<Like> likes = new ArrayList<>();

    private Integer scraps;

    @OneToOne
    @JoinColumn(name="scrap_id")
    @Nullable
    private Solution scrapId;

    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="algorithm_id")
    private Algorithm algorithm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="dataStructure_id")
    private DataStructure dataStructure;

    @Enumerated(EnumType.STRING)
    private Levels level;

    // 정적 팩토리 메소드
    public static Solution toEntity(SolutionRequest dto){

          Solution soultion = Solution.builder()
                  .title(dto.getSolutionTitle())
                  .problem(new Problem(null, dto.getProblemTitle(),dto.getProblemLink(), "백준"))
                  .pubilc(true) //API 스펙에 dto에 public이 없음
                  .code(dto.getCode())
                  .description(dto.getDescription())
                  .scraps(0)
                  .date(LocalDate.now())
                  .algorithm(new Algorithm(null, dto.getAlgorithm()))
                  .dataStructure(new DataStructure(null, dto.getDataStructure()))
                  .level(dto.getLevel())
                  .build();

        return soultion;
    }

}
