package com.prograngers.backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.prograngers.backend.exception.notfound.AlgorithmNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Algorithms {
    BUBBLE_SORT("버블정렬"),
    SELECTION_SORT("선택정렬"),
    INSERTION_SORT("삽입정렬"),
    HEAP_SORT("힙 정렬"),
    MERGE_SORT("병합 정렬"),
    QUICK_SORT("퀵 정렬"),
    LINEAR_SEARCH("선형 탐색"),
    BINARY_SEARCH("이진 탐색"),
    BFS("BFS"),
    DFS("DFS"),
    DIJKSTRA("다익스트라");

    private final String krName;


    @JsonCreator
    public static Algorithms from(String krName){
        for (Algorithms algorithm : Algorithms.values()){
            if (algorithm.getKrName().equals(krName)){
                return algorithm;
            }
        }
        throw new AlgorithmNotFoundException();
    }



}
