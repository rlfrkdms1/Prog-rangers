package com.prograngers.backend.dto;

import com.prograngers.backend.entity.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScarpSolutionRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
    private Levels level;

}
