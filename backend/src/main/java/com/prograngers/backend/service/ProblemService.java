package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.ProblemListResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemListResponse> getProblemList(Integer page, AlgorithmConstant algorithm, DataStructureConstant dataStructure, String sortBy) {
       List<Problem> list;
        if (sortBy == "date"){
           if (algorithm==null){
               list=problemRepository.findByDataStructureSortByDate(dataStructure);
           } if (dataStructure == null){
                list=problemRepository.findByAlgorithmSortByDate(algorithm);
           } if (dataStructure==null && algorithm ==null){
                list=problemRepository.findSortByDate();
           } else {
                list=problemRepository.findByAlgorithmAndDataStructureSortByDate(algorithm,dataStructure);
           }
       } else {
           if (algorithm==null){
               list=problemRepository.findByDataStructureSortBySolution(dataStructure);
           } if (dataStructure == null){
                list=problemRepository.findByAlgorithmSortBySolution(algorithm);
           } if (dataStructure==null && algorithm ==null){
                list=problemRepository.findSortBySolution();
           } else {
                list=problemRepository.findByAlgorithmAndDataStructureSortBySolution(algorithm,dataStructure);
           }
       }

        List<ProblemListResponse> problemListResponses = new ArrayList<>();
        // problemRepository.findAllByOrderByDateDesc();

        // 한 화면에 보여줄 풀이 개수
        int onePage = 4;
        // 페이지 시작 인덱스
        int start = onePage * (page - 1);
        // 페이지 끝 인덱스
        int end = onePage * (page - 1) + 4;

        for (int i = start; i < end; i++) {
            if (i >= list.size()) {
                break;
            }
            Problem problem = list.get(i);
            problemListResponses.add(new ProblemListResponse(problem.getTitle(), problem.getOjName()));
        }
        return problemListResponses;
    }
}
