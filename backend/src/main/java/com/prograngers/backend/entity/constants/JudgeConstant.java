package com.prograngers.backend.entity.constants;

import com.prograngers.backend.exception.notfound.ProblemLinkNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Getter
public enum JudgeConstant {

    백준("acmicpc"),
    프로그래머스("programmers");

    private final String value;

    public static JudgeConstant from(String link) {
        log.info(link);
        for (JudgeConstant judge : JudgeConstant.values()) {
            String judgeValue = judge.getValue();
            if (link.contains(judgeValue)) {
                return judge;
            }
        }
        throw new ProblemLinkNotFoundException();
    }
}
