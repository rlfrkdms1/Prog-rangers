package com.prograngers.backend.dto;

import com.prograngers.backend.entity.Algorithms;
import com.prograngers.backend.entity.DataStructures;
import com.prograngers.backend.entity.Solution;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolutionUpdateForm {

    private String title;
    private String link;
    private Algorithms algorithmName;
    private DataStructures dataStructureName;

    private String code;

    private String description;


    public static SolutionUpdateForm toDto(Solution target) {
        return SolutionUpdateForm.builder()
                .title(target.getTitle())
                .link(target.getProblem().getLink())
                .algorithmName(target.getAlgorithm().getName())
                .dataStructureName(target.getDataStructure().getName())
                .code(target.getCode())
                .description(target.getDescription())
                .build();
    }
}
