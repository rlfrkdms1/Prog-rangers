package com.prograngers.backend.service;

import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public Solution  save(){
        return new Solution();
    }

    public Solution update(){
        return new Solution();
    }

    public Solution delete(){
        return new Solution();
    }

    public List<Solution> index(Long id){
        List<Solution> list = new ArrayList<>();
        return list;
    }

}
