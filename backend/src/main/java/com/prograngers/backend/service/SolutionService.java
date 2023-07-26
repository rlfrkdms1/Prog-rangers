package com.prograngers.backend.service;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class SolutionService {

    private final SolutionRepository solutionRepository;

    public Solution  save(Solution solution){
        Solution saved = solutionRepository.save(solution);
        return saved;
    }

    public Solution update(Solution solution){
            Solution updated = solutionRepository.save(solution);
            return updated;
    }

    public Solution delete(Solution solution){
            solutionRepository.delete(solution);
            return solution;
    }

    public List<Solution> index(Member member){

        List<Solution> solutionList = (List<Solution>) solutionRepository.findAllByMember(member);

        return solutionList;
    }

    public Optional<Solution> findById(Long solutionId) {
        return solutionRepository.findById(solutionId);
    }
}
