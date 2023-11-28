package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class WriteSolutionRequest {

    @NotBlank(message = "문제 제목을 입력해주세요")
    private String problemTitle;

    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String solutionTitle;

    @NotBlank(message = "문제 링크를 입력해주세요")
    private String problemLink;

    @Min(value = 1, message = "레벨 값은 1 미만일 수 없습니다")
    @Max(value = 5, message = "레벨 값은 5 초과일 수 없습니다")
    @NotNull(message = "레벨 값을 입력해주세요")
    private Integer level;

    @Nullable
    private AlgorithmConstant algorithm;

    @Nullable
    private DataStructureConstant dataStructure;

    private LanguageConstant language;

    @NotNull(message = "공개 여부를 설정해주세요")
    private Boolean isPublic;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "소스 코드를 입력해주세요")
    private String code;

    public Problem toProblem(JudgeConstant judgeName) {
        return Problem.builder()
                .link(problemLink)
                .ojName(judgeName)
                .title(problemTitle)
                .solutions(new ArrayList<>())
                .build();
    }

    public Solution toSolution(Member member, Problem problem) {

        Solution solution = Solution.builder()
                .problem(problem)
                .member(member)
                .title(solutionTitle)
                .language(language)
                .isPublic(true)
                .code(code)
                .description(description)
                .createdAt(LocalDateTime.now())
                .level(level)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .description(description)
                .code(code)
                .isPublic(isPublic)
                .build();

        problem.getSolutions().add(solution);

        return solution;
    }
}
