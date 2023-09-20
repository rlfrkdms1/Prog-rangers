package com.prograngers.backend.entity.solution;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.prograngers.backend.exception.enumtype.AlgorithmNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AlgorithmConstant {
    BUBBLE_SORT("BUBBLE_SORT"),
    SELECTION_SORT("SELECTION_SORT"),
    INSERTION_SORT("INSERTION_SORT"),
    HEAP_SORT("HEAP_SORT"),
    MERGE_SORT("MERGE_SORT"),
    QUICK_SORT("QUICK_SORT"),
    LINEAR_SEARCH("LINEAR_SORT"),
    BINARY_SEARCH("BINARY_SEARCH"),
    BFS("BFS"),
    DFS("DFS"),
    DIJKSTRA("DIJKSTRA");

    private final String stringValue;

    @JsonCreator
    public static AlgorithmConstant from(String krName) {
        for (AlgorithmConstant algorithm : AlgorithmConstant.values()) {
            if (algorithm.getStringValue().equals(krName)) {
                return algorithm;
            }
        }
        throw new AlgorithmNotFoundException();
    }

    @JsonValue
    public String getStringValue() {
        return stringValue;
    }
}
