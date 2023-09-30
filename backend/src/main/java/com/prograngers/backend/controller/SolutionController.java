package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.response.ShowMySolutionDetailResponse;
import com.prograngers.backend.dto.solution.response.ShowSolutionDetailResponse;
import com.prograngers.backend.dto.solution.reqeust.UpdateSolutionRequest;
import com.prograngers.backend.dto.solution.reqeust.WriteSolutionRequest;
import com.prograngers.backend.dto.solution.response.ShowSolutionListResponse;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.entity.sortconstant.SortConstant;
import com.prograngers.backend.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

import static com.prograngers.backend.entity.sortconstant.SortConstant.Name.NEWEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class SolutionController {
  
    private final SolutionService solutionService;
  
    @Login
    @PostMapping("/solutions")
    public ResponseEntity<Void> write(@LoggedInMember Long memberId, @RequestBody @Valid WriteSolutionRequest request){
        Long saveId = solutionService.save(request, memberId);
        return ResponseEntity.created(URI.create("/api/v1/solutions" + saveId)).build();
    }

    @Login
    @PostMapping("/solutions/{scrapId}")
    public ResponseEntity<Void> scrap(@LoggedInMember Long memberId, @PathVariable Long scrapId, @RequestBody @Valid  ScarpSolutionRequest request) {
        Long saveId = solutionService.saveScrap(scrapId, request, memberId);
        return ResponseEntity.created(URI.create("/api/v1/solutions" + saveId)).build();
    }

    @Login
    @PatchMapping("/solutions/{solutionId}")
    public ResponseEntity<Void> update(@PathVariable Long solutionId, @LoggedInMember Long memberId,
                                    @RequestBody @Valid UpdateSolutionRequest request){
        solutionService.update(solutionId, request, memberId);
        return ResponseEntity.noContent().build();
    }

    @Login
    @DeleteMapping("/solutions/{solutionId}")
    public ResponseEntity<Void> delete(@PathVariable Long solutionId, @LoggedInMember Long memberId){
        solutionService.delete(solutionId, memberId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/solutions/{solutionId}")
    public ShowSolutionDetailResponse showDetail(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId) {
        return solutionService.getSolutionDetail(solutionId, memberId);
    }

<<<<<<< HEAD
    private ResponseEntity<Object> redirect(Long solutionId) {
        if (solutionId==null){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath())).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(getRedirectPath() + "/" + solutionId)).build();
=======
    @GetMapping("/mypage/solutions/{solutionId}")
    public ShowMySolutionDetailResponse showMyDetail(@PathVariable Long solutionId, @LoggedInMember Long memberId) {
        return solutionService.getMySolutionDetail(memberId, solutionId);
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }

    @GetMapping("/problems/{problemId}/solutions")
    public ShowSolutionListResponse showList(@PageableDefault(size = 5) Pageable pageable,
                                             @PathVariable Long problemId,
                                             @RequestParam(required = false) LanguageConstant language,
                                             @RequestParam(required = false) AlgorithmConstant algorithm,
                                             @RequestParam(required = false) DataStructureConstant dataStructure,
                                             @RequestParam(defaultValue = NEWEST) SortConstant sortBy
    ) {
        return solutionService.getSolutionList(pageable, problemId, language, algorithm, dataStructure, sortBy);
    }
}
