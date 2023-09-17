package com.prograngers.backend.support.fixture;

import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import static com.prograngers.backend.entity.solution.Solution.*;


@AllArgsConstructor
public enum SolutionFixture {

    공개_풀이("풀이제목",true,"풀이코드","풀이설명"),
    비공개_풀이("풀이제목",false,"풀이코드","풀이설명");
    private final String title;
    private final boolean isPublic;
    private final String code;
    private final String description;

    public SolutionBuilder 기본_정보_빌더_생성(){
        return builder()
                .title(title)
                .isPublic(isPublic)
                .code(code)
                .description(description);
    }

    public Solution 기본_정보_생성(
            Problem problem, Member member, LocalDateTime createdDate,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure,LanguageConstant language, Integer level){
        return 기본_정보_빌더_생성()
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .language(language)
                .level(level)
                .build();
    }

    public Solution 기본_정보_생성(
            Problem problem, Member member, LocalDateTime createdDate,
            LanguageConstant language, Integer level){
        return 기본_정보_빌더_생성()
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .language(language)
                .level(level)
                .build();
    }

    public Solution 스크랩_생성(Member member, LocalDateTime createdDate, Integer level, Solution scrapSolution){
        return 기본_정보_빌더_생성()
                .problem(scrapSolution.getProblem())
                .member(member)
                .createdAt(createdDate)
                .algorithm(scrapSolution.getAlgorithm())
                .dataStructure(scrapSolution.getDataStructure())
                .language(scrapSolution.getLanguage())
                .level(level)
                .scrapSolution(scrapSolution)
                .build();
    }

    public Solution 아이디_지정_생성(
            Long id, Problem problem, Member member, LocalDateTime createdDate,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure,LanguageConstant language, Integer level) {
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .language(language)
                .level(level)
                .build();
    }
}
