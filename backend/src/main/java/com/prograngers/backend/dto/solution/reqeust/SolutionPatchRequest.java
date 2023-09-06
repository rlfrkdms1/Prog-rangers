package com.prograngers.backend.dto.solution.reqeust;

import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionPatchRequest {
    @NotBlank(message = "풀이 제목을 입력해주세요")
    private String title;
    private AlgorithmConstant algorithmName;
    private DataStructureConstant dataStructureName;
    @NotBlank(message = "소스 코드를 입력해 주세요")
    private String code;

    @NotBlank(message = "풀이 설명을 입력해주세요")
    private String description;

    @Min(value = 1, message = "레벨 값은 1 미만일 수 없습니다")
    @Max(value = 5, message = "레벨 값은 5 초과일 수 없습니다")
    private int level;

    public Solution toSolution(Solution target) {
        target.update(title,algorithmName,dataStructureName,level,code,description);
        return target;
    }
}
