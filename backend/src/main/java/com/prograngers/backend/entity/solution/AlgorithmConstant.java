package com.prograngers.backend.entity.solution;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.ALGORITHM_NOT_EXISTS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.prograngers.backend.entity.HashTag;
import com.prograngers.backend.exception.EnumTypeException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AlgorithmConstant implements HashTag {
    BUBBLE_SORT,
    SELECTION_SORT,
    INSERTION_SORT,
    HEAP_SORT,
    MERGE_SORT,
    QUICK_SORT,
    LINEAR_SEARCH,
    BINARY_SEARCH,
    BFS,
    DFS,
    MATH,
    DYNAMIC_PROGRAMMING,
    GREEDY,
    BRUTE_FORCE,
    PREFIX_SUM,
    COMBINATORICS,
    DIVIDE_AND_CONQUER,
    TWO_POINTER,
    RECURSION,
    SLIDING_WINDOW,
    BELLMAN_FORD,
    DIJKSTRA;

    @JsonCreator
    public static AlgorithmConstant from(String value) {
        for (AlgorithmConstant algorithm : AlgorithmConstant.values()) {
            if (algorithm.name().equals(value)) {
                return algorithm;
            }
        }
        throw new EnumTypeException(ALGORITHM_NOT_EXISTS);
    }
}
