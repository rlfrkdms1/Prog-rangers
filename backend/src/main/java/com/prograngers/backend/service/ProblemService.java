package com.prograngers.backend.service;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<Problem> getProblemList() {
        problemRepository.
    }
}
