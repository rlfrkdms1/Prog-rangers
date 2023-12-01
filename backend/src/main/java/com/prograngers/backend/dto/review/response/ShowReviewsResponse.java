package com.prograngers.backend.dto.review.response;

import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ShowReviewsResponse {
    private String title;
    private AlgorithmConstant algorithm;
    private DataStructureConstant dataStructure;
    private List<CodeLineWithReview> codeLineWithReview;

    public static ShowReviewsResponse from(Solution solution, String[] lines) {
        ShowReviewsResponse showReviewsResponse =
                new ShowReviewsResponse(solution.getTitle(), solution.getAlgorithm(), solution.getDataStructure(),
                        new ArrayList<>());

        addLinesAtResponseDto(lines, showReviewsResponse);

        return showReviewsResponse;
    }

    private static void addLinesAtResponseDto(String[] lines, ShowReviewsResponse showReviewsResponse) {
        // 먼저 최종 응답 dto에 각 라인을 넣는다
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            showReviewsResponse.getCodeLineWithReview().add(CodeLineWithReview.from(lines[lineNumber], lineNumber + 1));
        }
    }
}
