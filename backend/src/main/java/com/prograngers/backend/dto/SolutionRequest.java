package com.prograngers.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequest {

    @NotBlank(message = "문제 제목을 입력해주세요")
    private String problemTitle;
    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String solutionTitle;
    @NotBlank(message = "문제 링크를 입력해주세요")
    private String problemLink;
    @NotBlank(message = "문제 난이도를 입력해주세요")
    private String  level;

    @NotBlank(message = "알고리즘을 입력해주세요")
    private String algorithm;

    @NotBlank(message = "자료구조를 입력해주세요")
    private String dataStructure;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "문제 소스 코드를 입력해주세요")
    private String code;

}
