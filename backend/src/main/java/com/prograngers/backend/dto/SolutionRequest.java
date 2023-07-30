package com.prograngers.backend.dto;

import com.prograngers.backend.entity.*;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequest {

    @NotBlank(message = "문제 제목을 입력해주세요")
    private String problemTitle;
    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String solutionTitle;
    @NotBlank(message = "문제 링크를 입력해주세요")
    private String problemLink;
    // @NotBlank(message = "문제 난이도를 입력해주세요")
    private LevelConstant level;

    // @NotBlank(message = "알고리즘을 입력해주세요")
    private AlgorithmConstant algorithm;

    // @NotBlank(message = "자료구조를 입력해주세요")
    private DataStructureConstant dataStructure;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @NotBlank(message = "소스 코드를 입력해주세요")
    private String code;

    public Solution toEntity() {
        /*
        파싱해서 ojname 알아내서 문제에 넣기
        로그인정보로 멤버 알아내서 넣기
        스크랩 여부 알아내서 넣기
         */

        // 입력 링크 파싱해서 저지 정보 알아내기 아닐 경우 ProblemLinkNotFoundException
        JudgeConstant judge = checkLink(problemLink);

        return Solution.builder()
                .problem(new Problem(null, problemTitle, problemLink, judge)) // 파싱해서 ojname 알아내야함
                .member(new Member()) // 로그인정보로 멤버를 알아내야함
                .title(solutionTitle)
                .isPublic(true)
                .code(code)
                .description(description)
                .scraps(0)
                .date(LocalDate.now())
                .level(level)
                .algorithm(new Algorithm(null, algorithm))
                .dataStructure(new DataStructure(null, dataStructure))
                .description(description)
                .scrapId(null) // 스크랩 하지 않은 Solution이므로 null로 놓는다
                .code(code)
                .build();
    }

    private JudgeConstant checkLink(String problemLink) {
        return JudgeConstant.from(problemLink);
    }
}
