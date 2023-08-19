package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.LevelConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ScarpSolutionPostRequest {

    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;
    private LevelConstant level;

    public Solution toEntity(Solution scrap){
        Solution solution = Solution.builder().
                level(level).
                description(description).
                title(title)
                // 위 내용까지 스크랩 한 사용자가 수정할 수 있는 내용
                .isPublic(true) //스크랩한 풀이이기 때문에 무조건 공개한다
                .problem(scrap.getProblem()).date(LocalDate.now()).member(null) //로그인정보로 member를 알도록 수정해야함
                .code(scrap.getCode()).
                scrapSolution(scrap).
                algorithm(scrap.getAlgorithm()).
                dataStructure(scrap.getDataStructure())
                .build();
        return solution;
    }

}
