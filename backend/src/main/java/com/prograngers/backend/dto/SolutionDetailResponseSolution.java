package com.prograngers.backend.dto;

import com.prograngers.backend.entity.Algorithms;
import com.prograngers.backend.entity.DataStructures;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDetailResponseSolution {
    Long id;
    String nickname;
    String title;
    String link;
    Algorithms algorithmName;
    DataStructures dataStructureName;
    String code;
    String description;
    Integer likes;
    Integer scraps;
    String scrapLink;
}
