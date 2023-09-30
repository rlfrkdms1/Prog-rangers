package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/solution/response/SolutionDetailSolution.java
=======

>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/solution/response/SolutionResponse.java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/solution/response/SolutionDetailSolution.java
public class SolutionDetailSolution {
    Long id;
    String nickname;
    String title;
    String link;
    AlgorithmConstant algorithmName;
    DataStructureConstant dataStructureName;
    String[] code;
    String description;
    Integer likes;
    Integer scraps;
    String scrapLink;

    boolean pushedLike;

    boolean scraped;
=======
@Builder
public class SolutionResponse {
    private Long id;
    private String nickname;
    private String title;
    private String link;
    private AlgorithmConstant algorithmName;
    private DataStructureConstant dataStructureName;
    private String[] code;
    private String description;
    private Integer likes;
    private Integer scraps;
    private Long scrapId;
    private boolean pushedLike;
    private boolean scraped;
    private boolean mine;

    public static SolutionResponse from(Solution solution, String nickname, String problemLink,
                                        int likeCount, int scrapCount, boolean pushedLike, boolean scraped, boolean mine, Long scrapId){
        return SolutionResponse.builder()
                .id(solution.getId())
                .nickname(nickname)
                .title(solution.getTitle())
                .link(problemLink)
                .algorithmName(solution.getAlgorithm())
                .dataStructureName(solution.getDataStructure())
                .code(solution.getCode().split("\n"))
                .description(solution.getDescription())
                .likes(likeCount)
                .scraps(scrapCount)
                .pushedLike(pushedLike)
                .scraped(scraped)
                .mine(mine)
                .scrapId(scrapId)
                .build();
    }
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/solution/response/SolutionResponse.java
}
