package com.prograngers.backend.entity;

import com.prograngers.backend.exception.notfound.ProblemLinkNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum Judges {
    백준("acmicpc"),
    프로그래머스("programmers");

    private final String link;

    public static Judges from(String link){
        log.info(link);
        for (Judges judge : Judges.values()){
            String judgeLink = judge.getLink();
            if (link.contains(judgeLink)){
              return judge;
          }
        }
        throw new ProblemLinkNotFoundException();
    }

}
