package com.prograngers.backend.dto.review;

import java.util.ArrayList;
import java.util.List;

public class ReviewResponse {
    Long id;
    String nickname;
    String photo;
    String content;

    List<ReplyResponse> replies = new ArrayList<>();
}
