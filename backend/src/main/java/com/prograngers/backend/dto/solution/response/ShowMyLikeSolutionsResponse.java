package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
@Builder
public class ShowMyLikeSolutionsResponse {

    private boolean hasNext;
    private List<LikeSolutionResponse> contents;

    public static ShowMyLikeSolutionsResponse from(Slice<Solution> solutions) {
        return ShowMyLikeSolutionsResponse.builder()
                .hasNext(solutions.hasNext())
                .contents(solutions.getContent().stream().map(LikeSolutionResponse::from).collect(Collectors.toList()))
                .build();
    }
}
