package com.prograngers.backend.dto.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class ReviewResponse {
    Long id;
    String nickname;
    String photo;
    String content;
    List<ReplyResponse> replies = new ArrayList<>();
}
