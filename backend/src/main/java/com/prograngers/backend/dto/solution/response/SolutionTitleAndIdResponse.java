package com.prograngers.backend.dto.solution.response;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SolutionTitleAndIdResponse {
    private String title;
    private Long id;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SolutionTitleAndIdResponse solutionTitleAndIdResponse = (SolutionTitleAndIdResponse) obj;
        return Objects.equals(id, solutionTitleAndIdResponse.id);
    }

    public static SolutionTitleAndIdResponse from(String title, Long id) {
        return SolutionTitleAndIdResponse.builder()
                .title(title)
                .id(id)
                .build();
    }
}
