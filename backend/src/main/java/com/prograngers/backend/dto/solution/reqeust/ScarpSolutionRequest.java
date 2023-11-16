package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScarpSolutionRequest {

    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @Min(value = 1, message = "레벨 값은 1 미만일 수 없습니다")
    @Max(value = 5, message = "레벨 값은 5 초과일 수 없습니다")
    @NotNull(message = "레벨 값을 입력해주세요")
    private Integer level;

    public Solution toSolution(Solution scrap, Member member) {
        return Solution.builder()
                .level(level).
                description(description).
                title(title)
                // 위 내용까지 스크랩 한 사용자가 수정할 수 있는 내용
                .isPublic(false) //스크랩한 풀이이기 때문에 무조건 비공개한다
                .problem(scrap.getProblem())
                .createdAt(LocalDateTime.now())
                .code(scrap.getCode()).
                scrapSolution(scrap).
                algorithm(scrap.getAlgorithm()).
                dataStructure(scrap.getDataStructure())
                .language(scrap.getLanguage())
                .member(member)
                .build();
    }

}
