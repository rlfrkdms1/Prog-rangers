package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.solution.Solution.SolutionBuilder;
import static com.prograngers.backend.entity.solution.Solution.builder;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum SolutionFixture {

    공개_풀이("풀이제목", true, "import\nmain\nhello world\nreturn", "풀이설명"),
    비공개_풀이("풀이제목", false, "import\nmain\nhello world\nreturn", "풀이설명");
    private final String title;
    private final boolean isPublic;
    private final String code;
    private final String description;

    public SolutionBuilder 기본_정보_빌더_생성() {
        return builder()
                .title(title)
                .isPublic(isPublic)
                .code(code)
                .description(description);
    }

    public Solution 태그_추가_생성(
            Problem problem, Member member, LocalDateTime createdDate,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure, LanguageConstant language,
            Integer level) {
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
            LanguageConstant language, Integer level) {
        return 기본_정보_빌더_생성()
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .language(language)
                .level(level)
                .build();
    }

    public Solution 스크랩_생성(Member member, LocalDateTime createdDate, Integer level, Solution scrapSolution) {
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

    public Solution 스크랩_아이디_지정_생성(Long id, Member member, LocalDateTime createdDate, Integer level,
                                  Solution scrapSolution) {
        return 기본_정보_빌더_생성()
                .id(id)
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
            AlgorithmConstant algorithm, DataStructureConstant dataStructure, LanguageConstant language,
            Integer level) {
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

    public Solution 아이디_지정_생성(
            Long id, Problem problem, Member member, LocalDateTime createdDate,
            LanguageConstant language, Integer level) {
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .language(language)
                .level(level)
                .build();
    }

    public Solution 풀이_제목_지정_생성(String title, Problem problem, Member member, LocalDateTime createdDate,
                                LanguageConstant language, Integer level) {
        return 기본_정보_빌더_생성()
                .title(title)
                .problem(problem)
                .member(member)
                .createdAt(createdDate)
                .language(language)
                .level(level)
                .build();
    }
}
