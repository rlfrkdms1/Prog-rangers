package com.prograngers.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ReplyResponse {
    private Long id;
    private String nickname;
    private String photo;
    private String content;
}
