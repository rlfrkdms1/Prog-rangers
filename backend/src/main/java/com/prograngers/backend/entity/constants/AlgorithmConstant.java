package com.prograngers.backend.entity.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.AlgorithmNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AlgorithmConstant {
    BUBBLE_SORT("버블정렬"),
    SELECTION_SORT("선택정렬"),
    INSERTION_SORT("삽입정렬"),
    HEAP_SORT("힙정렬"),
    MERGE_SORT("병합정렬"),
    QUICK_SORT("퀵정렬"),
    LINEAR_SEARCH("선형탐색"),
    BINARY_SEARCH("이진탐색"),
    BFS("BFS"),
    DFS("DFS"),
    DIJKSTRA("다익스트라");

    private final String krName;


    @JsonCreator
    public static AlgorithmConstant from(String krName) {
        for (AlgorithmConstant algorithm : AlgorithmConstant.values()) {
            if (algorithm.getKrName().equals(krName)) {
                return algorithm;
            }
        }
        throw new AlgorithmNotFoundException();
    }

    @JsonValue
    public String getKrName() {
        return krName;
    }
}
