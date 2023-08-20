package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LanguageConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public enum SolutionFixture {
    풀이1("풀이제목1", true, "풀이코드1", "풀이설명1", null, LevelConstant.ONE, LocalDateTime.now()),
    풀이2("풀이제목2", true, "풀이코드2", "풀이설명2", null, LevelConstant.ONE, LocalDateTime.now().plusDays(1)),
    풀이3("풀이제목3", true, "풀이코드3", "풀이설명3", null, LevelConstant.ONE, LocalDateTime.now().plusDays(2)),

    풀이4("풀이제목4", true, "풀이코드4", "풀이설명4", null, LevelConstant.ONE, LocalDateTime.now().plusDays(3)),
    풀이5("풀이제목5", true, "풀이코드5", "풀이설명5", null, LevelConstant.ONE, LocalDateTime.now().plusDays(4)),
    풀이6("풀이제목6", true, "풀이코드6", "풀이설명6", null, LevelConstant.ONE, LocalDateTime.now().plusDays(5)),

    풀이7("풀이제목7", true, "풀이코드7", "풀이설명7", null, LevelConstant.ONE, LocalDateTime.now().plusDays(6)),
    풀이8("풀이제목8", true, "풀이코드8", "풀이설명8", null, LevelConstant.ONE, LocalDateTime.now().plusDays(7)),
    풀이9("풀이제목9", true, "풀이코드9", "풀이설명9", null, LevelConstant.ONE, LocalDateTime.now().plusDays(8));
    private final String title;
    private final boolean isPublic;
    private final String code;
    private final String description;
    private final List<Likes> likes;
    private final LevelConstant level;
    private final LocalDateTime date;

    public Solution.SolutionBuilder 기본_정보_빌더_생성() {
        return Solution.builder()
                .title(this.title)
                .isPublic(this.isPublic)
                .code(this.code)
                .description(this.description)
                .date(this.date)
                .level(this.level.getLevel());
    }


    public Solution 스크랩_솔루션_생성(
            Long id, Problem problem, Member member, Solution scrapSolution, Integer scraps,
            AlgorithmConstant algorithm, DataStructureConstant dataStructure
    ) {
        return 기본_정보_빌더_생성()
                .id(id)
                .problem(problem)
                .member(member)
                .scrapSolution(scrapSolution)
                .algorithm(algorithm)
                .dataStructure(dataStructure)
                .build();
    }

    public Solution 일반_솔루션_생성(
            Long id, Problem problem, Member member, Integer scraps,
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
            Long id, Problem problem, Member member, Integer scraps,
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

}
