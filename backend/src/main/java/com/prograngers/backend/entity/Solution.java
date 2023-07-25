package com.prograngers.backend.entity;

import com.prograngers.backend.dto.SolutionRequest;
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
    @JoinColumn(name="problem_id")
    private Problem problem;

    @ManyToOne
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

    @OneToOne
    @JoinColumn(name="algorithm_id")
    private Algorithm algorithm;

    @OneToOne
    @JoinColumn(name="dataStructure_id")
    private DataStructure dataStructure;

    @Enumerated(EnumType.STRING)
    private Level level;

    // 정적 팩토리 메소드
    public static Solution toEntity(SolutionRequest dto){

        Level level;
        String dtoLevel = dto.getLevel();
        if (dtoLevel.equals("ONE")) {
            level = Level.ONE;
        } else if (dtoLevel.equals("TWO")) {
            level = Level.TWO;
        } else if (dtoLevel.equals("THREE")){
            level = Level.THREE;
        } else if (dtoLevel.equals("FOUR")){
            level = Level.FOUR;
        } else {
            level = Level.FIVE;
        }

          Solution soultion = Solution.builder()
                  .title(dto.getProblemTitle())
                  .pubilc(true) //API 스펙에 dto에 public이 없음
                  .code(dto.getCode())
                  .description(dto.getDescription())
                  .likes(null)
                  .scraps(0)
                  .scrapId(null)
                  .date(LocalDate.now())
                  .algorithm(new Algorithm(null, dto.getAlgorithm()))
                  .dataStructure(new DataStructure(null, dto.getDataStructure()))
                  .level(level)
                  .build();

        return soultion;
    }

}
