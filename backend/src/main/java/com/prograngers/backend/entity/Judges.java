package com.prograngers.backend.entity;

import com.prograngers.backend.exception.notfound.ProblemLinkNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Judges {
    백준("acmicpc.net/problem"),
    프로그래머스("programmers.co.kr/learn/courses");

    private final String link;

    public static Judges from(String link){
        for (Judges judge : Judges.values()){
          if (judge.getLink().contains(link)){
              return judge;
          }
        }
        throw new ProblemLinkNotFoundException();
    }

}
