package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.response.ShowMySolutionListResponse;
import com.prograngers.backend.dto.solution.response.SolutionDetailResponse;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.LanguageConstant;
import com.prograngers.backend.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class SolutionController {

    private final SolutionService solutionService;
    private static final String DEFAULT_PAGE = "1";

    @Login
    @PostMapping("/solutions")
    public ResponseEntity<Void> write(@LoggedInMember Long memberId, @RequestBody @Valid SolutionPostRequest solutionPostRequest) {
        Long saveId = solutionService.save(solutionPostRequest, memberId);
        return ResponseEntity.created(URI.create("/api/v1/solutions" + saveId)).build();
    }

    @Login
    @PostMapping("/solutions/{scrapId}")
    public ResponseEntity<Void> scrap(@LoggedInMember Long memberId, @PathVariable Long scrapId, @RequestBody @Valid ScarpSolutionPostRequest request) {
        Long saveId = solutionService.saveScrap(scrapId, request, memberId);
        return ResponseEntity.created(URI.create("/api/v1/solutions" + saveId)).build();
    }

    @Login
    @PatchMapping("/solutions/{solutionId}")
    public ResponseEntity<Void> update(@PathVariable Long solutionId, @LoggedInMember Long memberId,
                                       @RequestBody @Valid SolutionPatchRequest solutionPatchRequest) {
        solutionService.update(solutionId, solutionPatchRequest, memberId);
        return ResponseEntity.noContent().build();
    }

    @Login
    @DeleteMapping("/solutions/{solutionId}")
    public ResponseEntity<Void> delete(@PathVariable Long solutionId, @LoggedInMember Long memberId) {
        solutionService.delete(solutionId, memberId);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/solutions/{solutionId}")
    public ResponseEntity<?> showDetail(@PathVariable Long solutionId, @LoggedInMember(required = false) Long memberId) {
        SolutionDetailResponse solutionDetailResponse = solutionService.getSolutionDetail(solutionId, memberId);
        return ResponseEntity.ok().body(solutionDetailResponse);
    }

    @Login
    @GetMapping("/mypage/solutions")
    public ShowMySolutionListResponse showMyList(@RequestParam(required = false) String keyword,
                                                 @RequestParam(required = false) LanguageConstant language,
                                                 @RequestParam(required = false) AlgorithmConstant algorithm,
                                                 @RequestParam(required = false) DataStructureConstant dataStructure,
                                                 @RequestParam(required = false) Integer level,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @LoggedInMember Long memberId) {
        return solutionService.getMyList(keyword, language, algorithm, dataStructure, level, page, memberId);
    }

}
