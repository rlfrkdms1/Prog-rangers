package com.prograngers.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequest {

    @NotBlank
    private String problemTitle;
    @NotBlank
    private String solutionTitle;
    @NotBlank
    private String problemLink;
    @NotBlank
    private String  level;

    @NotBlank
    private String algorithm;

    @NotBlank
    private String dataStructure;

    @NotBlank
    private String description;

    @NotBlank
    private String code;

}
