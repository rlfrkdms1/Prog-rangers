package com.prograngers.backend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDetailResponseComment {
    String nickname;
    Integer orderParent;
    Integer orderChild;
    String content;
    String mention;
}
