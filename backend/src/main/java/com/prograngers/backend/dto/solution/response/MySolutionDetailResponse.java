package com.prograngers.backend.dto.solution.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;

import java.util.List;

public class MySolutionDetailResponse {
    private String problemTitle;
    private JudgeConstant ojName;
    private List<Object> tags;

    private String description;

    private String[] code;

    private int likes;
    private int  scraps;

    private List<Comment> comments;

    private List<String> recommendedSolutionTitle;

    private List<MySolutionDetailSolution> mySolutionList;

    private List<MySolutionDetailSolution> myScrapSolutionList;

}
