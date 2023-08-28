package com.prograngers.backend.entity.problem;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String link;

    @Enumerated(EnumType.STRING)
    private JudgeConstant ojName;

    @OneToMany(mappedBy = "problem")
    @Builder.Default
    List<Solution> solutions = new ArrayList<>();

    public void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void updateLink(String link) {
        if (link != null) {
            this.link = link;
        }
    }

    public void updateOjName(JudgeConstant ojName) {
        if (ojName != null) {
            this.ojName = ojName;
        }
    }
}
