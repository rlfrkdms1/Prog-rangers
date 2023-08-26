package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public enum SolutionFixture {
    풀이1("풀이제목1", true, "풀이코드1", "풀이설명1", 1, LocalDateTime.now()),
    풀이2("풀이제목2", true, "풀이코드2", "풀이설명2", 1, LocalDateTime.now().plusDays(1)),
    풀이3("풀이제목3", true, "풀이코드3", "풀이설명3",1, LocalDateTime.now().plusDays(2)),

    풀이4("풀이제목4", true, "풀이코드4", "풀이설명4", 1, LocalDateTime.now().plusDays(3)),
    풀이5("풀이제목5", true, "풀이코드5", "풀이설명5", 1, LocalDateTime.now().plusDays(4)),
    풀이6("풀이제목6", true, "풀이코드6", "풀이설명6", 1, LocalDateTime.now().plusDays(5)),

    풀이7("풀이제목7", true, "풀이코드7", "풀이설명7", 1, LocalDateTime.now().plusDays(6)),
    풀이8("풀이제목8", true, "풀이코드8", "풀이설명8",1, LocalDateTime.now().plusDays(7)),
    풀이9("풀이제목9", true, "풀이코드9", "풀이설명9", 1, LocalDateTime.now().plusDays(8));
    private final String title;
    private final boolean isPublic;
    private final String code;
    private final String description;
    private final Integer level;
    private final LocalDateTime date;

    public Solution.SolutionBuilder 기본_정보_빌더_생성() {
        return Solution.builder()
                .title(this.title)
                .isPublic(this.isPublic)
                .code(this.code)
                .description(this.description)
                .createdDate(this.date)
                .level(this.level);
    }

    public Solution 일반_솔루션_생성(
            Long id, Problem problem, Member member,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure
            ) {
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .build();
    }

    public Solution 기본_솔루션_생성(Long id){
        return 기본_정보_빌더_생성()
                .id(id)
                .build();
    }

    public Solution 기본_솔루션_생성(){
        return 기본_정보_빌더_생성()
                .id(null)
                .build();
    }

    public Solution 언어_포함_솔루션_생성(
            Long id, Problem problem, Member member,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure,
            LanguageConstant language
    ){
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .language(language)
                .build();
    }

    public Solution 스크랩_포함_솔루션_생성(
            Long id, Problem problem, Member member,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure,
            LanguageConstant language, Solution scrapSolution
    ){
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .language(language)
                .scrapSolution(scrapSolution)
                .build();
    }


}
