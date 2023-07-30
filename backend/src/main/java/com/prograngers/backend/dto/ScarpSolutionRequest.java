package com.prograngers.backend.dto;

import com.prograngers.backend.entity.constants.LevelConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScarpSolutionRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
    private LevelConstant level;

}
