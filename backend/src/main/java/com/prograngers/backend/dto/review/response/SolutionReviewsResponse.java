package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class SolutionReviewsResponse {
    private String title;
    private AlgorithmConstant algorithm;
    private DataStructureConstant dataStructure;
    private List<Line> lines;

    public static SolutionReviewsResponse from(Solution solution, String[] lines) {
        SolutionReviewsResponse solutionReviewsResponse =
                new SolutionReviewsResponse(solution.getTitle(), solution.getAlgorithm(),solution.getDataStructure(), new ArrayList<>());
        // 먼저 최종 응답 dto에 각 라인을 넣는다
        for (int lineNumber = 1; lineNumber <= lines.length-1; lineNumber++) {
            Line line = Line.builder()
                    .codeLineNumber(lineNumber )
                    .code(lines[lineNumber])
                    .build();
            solutionReviewsResponse.getLines().add(line);
        }
        return  solutionReviewsResponse;
    }
}
