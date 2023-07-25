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
        Optional<Solution> optionalTarget = solutionRepository.findById(solution.getId());
        Solution target = optionalTarget.get();
        if (target==null){
            return null;
        } else {
            Solution updated = solutionRepository.save(solution);
            return updated;
        }
    }

    public Solution delete(Solution solution){
        Optional<Solution> optionalTarget = solutionRepository.findById(solution.getId());
        Solution target = optionalTarget.get();
        if (target==null){
            return null;
        } else {
            solutionRepository.delete(solution);
            return target;
        }
    }

    public List<Solution> index(Member member){

        List<Solution> solutionList = (List<Solution>) solutionRepository.findAllByMember(member);

        return solutionList;
    }

}
