package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDetailResponseSolution {
    Long id;
    String nickname;
    String title;
    String link;
    AlgorithmConstant algorithmName;
    DataStructureConstant dataStructureName;
    String code;
    String description;
    Integer likes;
    Integer scraps;
    String scrapLink;
}
