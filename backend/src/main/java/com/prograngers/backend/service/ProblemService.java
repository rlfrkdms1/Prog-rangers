package com.prograngers.backend.service;

import com.prograngers.backend.dto.problem.ProblemAlgorithmDataStructureResponse;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.problem.dto.ProblemResponse;
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

//    public List<ProblemAlgorithmDataStructureResponse> getProblemList(Integer page, AlgorithmConstant algorithm, DataStructureConstant dataStructure, String sortBy) {
//        // 알고리즘, 자료구조필터에 따른 문제 목록을 가져온다
//        // 정렬 기준에따라 정렬한다
//        log.info("쿼리 스트링 algorithm : {}", algorithm);
//        log.info("쿼리 스트링 dataStructure : {}", dataStructure);
//        log.info("쿼리 스트링 sortBy : {}", sortBy);
//        List<Problem> list;
//        if (sortBy.equals("date")) {
//            if (dataStructure == null && algorithm == null) {
//                list = problemRepository.findSortByDate();
//            } else if (algorithm == null) {
//                list = problemRepository.findByDataStructureSortByDate(dataStructure);
//            } else if (dataStructure == null) {
//                log.info("findByAlgorithmSortByDate");
//                list = problemRepository.findByAlgorithmSortByDate(algorithm);
//            } else {
//                log.info("findByAlgorithmAndDataStructureSortBydATE");
//                list = problemRepository.findByAlgorithmAndDataStructureSortByDate(algorithm, dataStructure);
//            }
//        } else {
//            if (dataStructure == null && algorithm == null) {
//                list = problemRepository.findSortBySolution();
//            } else if (algorithm == null) {
//                list = problemRepository.findByDataStructureSortBySolution(dataStructure);
//            } else if (dataStructure == null) {
//                list = problemRepository.findByAlgorithmSortBySolution(algorithm);
//            } else {
//                list = problemRepository.findByAlgorithmAndDataStructureSortBySolution(algorithm, dataStructure);
//            }
//        }
//
//        /*
//        필터 조건에 맞게 가져온 Problem list에 대해 상위 3가지 알고리즘, 자료구조를 찾아 응답 dto로 만들어 리스트에 넣는다
//         */
//
//        List<ProblemAlgorithmDataStructureResponse> problemResponses = new ArrayList<>();
//
//        for (Problem problem : list) {
//            // 문제 이름, 저지명 세팅
//            ProblemAlgorithmDataStructureResponse problemAlgorithmDataStructureResponse = new ProblemAlgorithmDataStructureResponse();
//            problemAlgorithmDataStructureResponse.setTitle(problem.getTitle());
//            problemAlgorithmDataStructureResponse.setOjName(problem.getOjName());
//
//            Long problemId = problem.getId();
//            List<AlgorithmConstant> algorithms = problemRepository.getTopAlgorithms(problemId);
//            List<DataStructureConstant> dataStructures = problemRepository.getTopDataStructures(problemId);
//
//            for (AlgorithmConstant ac : algorithms) {
//                problemAlgorithmDataStructureResponse.getAlgorithms().add(ac);
//            }
//            for (DataStructureConstant dc : dataStructures) {
//                problemAlgorithmDataStructureResponse.getDataStructures().add(dc);
//            }
//
//            problemResponses.add(problemAlgorithmDataStructureResponse);
//        }
//        // 페이지에 필요한 부분만 응답
//        List<ProblemAlgorithmDataStructureResponse> problemListResponses = new ArrayList<>();
//
//        // 한 화면에 보여줄 풀이 개수
//        int onePage = 4;
//        // 페이지 시작 인덱스
//        int start = onePage * (page - 1);
//        // 페이지 끝 인덱스
//        int end = onePage * (page - 1) + 4;
//
//        for (int i = start; i < end; i++) {
//            if (i >= list.size()) {
//                break;
//            }
//            ProblemAlgorithmDataStructureResponse problemAlgorithmDataStructureResponse = problemResponses.get(i);
//            problemListResponses.add(problemAlgorithmDataStructureResponse);
//        }
//        return problemListResponses;
//    }

    public List<ProblemResponse> getProblemList(
            Integer page,
            AlgorithmConstant algorithm,
            DataStructureConstant dataStructure,
            String sortBy){
        return problemRepository.searchByAlgorithmAndDataStructureOrderByDateDesc(page,dataStructure,algorithm,sortBy);
    }
}
