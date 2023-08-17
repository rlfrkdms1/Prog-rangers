package com.prograngers.backend.dto.solution.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDetailComment {
    String nickname;
    String content;
    String mention;
}
