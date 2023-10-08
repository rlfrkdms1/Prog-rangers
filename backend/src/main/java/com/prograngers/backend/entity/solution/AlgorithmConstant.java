package com.prograngers.backend.entity.solution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.enumtype.AlgorithmNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AlgorithmConstant implements HashTag {
    BUBBLE_SORT("버블 정렬"),
    SELECTION_SORT("선택 정렬"),
    INSERTION_SORT("삽입 정렬"),
    HEAP_SORT("힙 정렬"),
    MERGE_SORT("병합 정렬"),
    QUICK_SORT("퀵 정렬"),
    LINEAR_SEARCH("선형 정렬"),
    BINARY_SEARCH("이진 탐색"),
    BFS("BFS"),
    DFS("DFS"),
    DIJKSTRA("다익스트라");

    private final String view;

    public static AlgorithmConstant from(String value) {
        for (AlgorithmConstant algorithm : AlgorithmConstant.values()) {
            if (algorithm.getView().equals(value)) {
                return algorithm;
            }
        }
        throw new AlgorithmNotFoundException();
    }

    public String getView() {
        return view;
    }
}
