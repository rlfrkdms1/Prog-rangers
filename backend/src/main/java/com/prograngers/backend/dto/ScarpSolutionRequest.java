package com.prograngers.backend.dto;

import com.prograngers.backend.entity.constants.LevelConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScarpSolutionRequest {

    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;
    private LevelConstant level;

}
