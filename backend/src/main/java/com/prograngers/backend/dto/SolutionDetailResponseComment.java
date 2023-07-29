package com.prograngers.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SolutionDetailResponseComment {
    String nickname;
    Integer orderParent;
    Integer orderChild;
    String content;
    String mention;
}
