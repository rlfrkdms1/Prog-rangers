package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.problem.ProblemRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class SolutionPostRequest {

    @NotBlank(message = "문제 제목을 입력해주세요")
    private String problemTitle;
    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String solutionTitle;
    @NotBlank(message = "문제 링크를 입력해주세요")
    private String problemLink;
    @Min(value = 1, message = "레벨 값은 1 미만일 수 없습니다")
    @Max(value = 5, message = "레벨 값은 5 초과일 수 없습니다")
    private Integer level;

    private AlgorithmConstant algorithm;

    private DataStructureConstant dataStructure;

    private LanguageConstant language;

    @NotNull(message = "공개 여부를 설정해주세요")
    private Boolean isPublic;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "소스 코드를 입력해주세요")
    private String code;

    public Problem toProblem(){
        // 유효한 문제 링크인지 확인
        JudgeConstant judge = checkLink(problemLink);

        return Problem.builder()
                .link(problemLink)
                .ojName(judge)
                .title(problemTitle)
                .solutions(new ArrayList<>())
                .build();
    }

    public Solution toSolution(Problem problem, Member member) {

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

    public static SolutionPostRequest from(Solution solution){
        return SolutionPostRequest.builder()
                .algorithm(solution.getAlgorithm())
                .code(solution.getCode())
                .level(solution.getLevel())
                .isPublic(solution.isPublic())
                .description(solution.getDescription())
                .dataStructure(solution.getDataStructure())
                .language(solution.getLanguage())
                .problemLink(solution.getProblem().getLink())
                .problemTitle(solution.getProblem().getTitle())
                .solutionTitle(solution.getTitle())
                .build();
    }

    private JudgeConstant checkLink(String problemLink) {
        return JudgeConstant.from(problemLink);
    }

}
