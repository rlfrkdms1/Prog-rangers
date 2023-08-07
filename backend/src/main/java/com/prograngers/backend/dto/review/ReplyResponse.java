package com.prograngers.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@Builder
public class ReplyResponse {
    private Long id;
    private String nickname;
    private String photo;
    private String content;
}
