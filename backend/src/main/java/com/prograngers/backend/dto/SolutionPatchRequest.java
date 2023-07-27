package com.prograngers.backend.dto;

import com.prograngers.backend.entity.Algorithm;
import com.prograngers.backend.entity.DataStructure;
import com.prograngers.backend.entity.Solution;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionPatchRequest {
    @NotBlank(message = "문제 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "알고리즘 이름을 입력해 주세요")
    private String algorithmName;

    @NotBlank(message = "자료구조 이름을 입력해 주세요")
    private String dataStructureName;
    @NotBlank(message = "문제 소스 코드를 입력해 주세요")
    private String code;

    @NotBlank(message = "풀이 설명을 입력해 주세요")
    private String description;

    public Solution toEntity(Solution target) {
        return Solution.builder()
                .id(target.getId())
                .problem(target.getProblem())
                .member(target.getMember())
                .title(title)
                .pubilc(target.isPubilc())
                .description(description)
                .likes(target.getLikes())
                .scraps(target.getScraps())
                .scrapId(target.getScrapId())
                .date(target.getDate())
                .algorithm(new Algorithm(null,target.getAlgorithm().getName()))
                .dataStructure(new DataStructure(null, target.getDataStructure().getName()))
                .level(target.getLevel())
                .code(code)
                .description(description)
                .build();
    }
}
