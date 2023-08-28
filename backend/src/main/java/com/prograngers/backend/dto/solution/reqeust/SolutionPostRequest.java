package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "소스 코드를 입력해주세요")
    private String code;

    public Solution toSolution() {
        /*
        파싱해서 ojname 알아내서 문제에 넣기
        로그인정보로 멤버 알아내서 넣기
        스크랩 여부 알아내서 넣기
         */

        // 입력 링크 파싱해서 저지 정보 알아내기 아닐 경우 ProblemLinkNotFoundException
        JudgeConstant judge = checkLink(problemLink);

        return Solution.builder()
                .problem(new Problem(null, problemTitle, problemLink, judge, null))
                .member(new Member()) // 로그인정보로 멤버를 알아내야함
                .title(solutionTitle)
                .language(language)
                .isPublic(true)
                .code(code)
                .description(description)
                .createdDate(LocalDateTime.now())
                .level(level)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .description(description)
                .scrapSolution(null) // 스크랩 하지 않은 Solution이므로 null로 놓는다
                .code(code)
                .build();
    }

    public static SolutionPostRequest from(Solution solution){
        return SolutionPostRequest.builder()
                .algorithm(solution.getAlgorithm())
                .code(solution.getCode())
                .level(solution.getLevel())
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
