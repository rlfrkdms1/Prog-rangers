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

    private boolean isPublic;

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

    public void updateProblem(Problem problem){
        if (problem!=null){
            this.problem = problem;
        }
    }
    public void updateMember(Member member){
        if (member!=null){
            this.member = member;
        }
    }

    public void updateCode(String code){
        if (!code.isEmpty()){
            this.code = code;
        }
    }

    public void updateDescription(String description){
        if (!code.isEmpty()){
            this.description = description;
        }
    }

    public void updateLike(Like like){
        if (like!=null){
            this.likes.add(like);
        }
    }
    public void upScraps(){
        this.scraps+=1;
    }
    public void downScraps(){
        this.scraps-=1;
    }

    public void updateScrapId(Solution solution){
        if (solution!=null){
            this.scrapId = solution;
        }
    }

    public void updateAlgorithm(Algorithms algorithm){
        this.algorithm = new Algorithm(null,algorithm);
    }

    public void updateDataStructure(DataStructures dataStructure){
        this.dataStructure = new DataStructure(null,dataStructure);
    }

    public void updateLevel(Levels level){
        this.level = level;
    }
}
