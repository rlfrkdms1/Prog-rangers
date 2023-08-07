package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.ProblemListResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemListResponse> getProblemList(Long page) {
        List<ProblemListResponse> problemListResponses = new ArrayList<>();
        List<Problem> list = problemRepository.findAllByOrderByDateDesc();

        // 전체 페이지 개수 계산
        double totalPage = Math.ceil(list.size() / 4);
        // 한 화면에 보여질 페이지 그룹
        double pageGroup = Math.ceil(page / 5);
        // 화면에 보여줄 페이지 그룹의 첫번째 페이지 번호
        int firstOfPageGroup = (int)((pageGroup - 1) * 4 + 1);
        // 화면에 보여줄 마지막 페이지 그룹의 마지막 페이지 번호
        int lastOfPageGroup = (int)(pageGroup * 4);

        for (int i=firstOfPageGroup+1; i<=lastOfPageGroup+1; i++){
            Problem problem = list.get(i);
            problemListResponses.add(new ProblemListResponse(problem.getTitle(), problem.getOjName()));
        }

        return problemListResponses;
    }
}
