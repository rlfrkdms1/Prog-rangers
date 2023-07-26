package com.prograngers.backend.controller;

import com.prograngers.backend.dto.ErrorResponse;
import com.prograngers.backend.dto.SolutionPatchRequest;
import com.prograngers.backend.dto.SolutionRequest;
import com.prograngers.backend.dto.SolutionUpdateForm;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solutions")
@Slf4j
public class SolutionController {
    private final SolutionService solutionService;

    // solution 쓰기
    @PostMapping("/new-form")
    public ResponseEntity<?> newForm(@RequestBody @Valid SolutionRequest solutionRequest) throws URISyntaxException {

        // Valid 확인 -> 검증 실패할 경우 MethodArgumentNotValidException

        // 리포지토리 활용해 저장
        Solution solution = Solution.toEntity(solutionRequest);
        Solution saved = solutionService.save(solution);
        Long solutionId = saved.getId();

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI("http://localhost:8080/solutions/"+solutionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // 수정 폼 반환
    @GetMapping("/{solutionId}/update-form")
    public ResponseEntity<?> updateForm(@PathVariable Long solutionId){
        Optional<Solution> optionalTarget = solutionService.findById(solutionId);
        Solution target = optionalTarget.orElseThrow(()->new NoSuchElementException("풀이를 찾을 수 없습니다"));

        return ResponseEntity.ok().body(SolutionUpdateForm.toDto(target));
    }

    // 수정 요청
    @PatchMapping("/{solutionId}")
    public ResponseEntity<?> update(@PathVariable Long solutionId,
                                    @RequestBody @Valid SolutionPatchRequest solutionPatchRequest) throws URISyntaxException {
        // SolutionPatchRequest에서 Valid 검증에 실패할 경우 MethodArgumentNotValidException을 던지고 ExControllerAdvice에서 처리한다

        // solutionId에 해당하는 solution  찾기, 없을 경우 NoSuchElementException을 던지고 ExControllerAdvice에서 처리한다
        Optional<Solution> optionalTarget = solutionService.findById(solutionId);
        Solution target = optionalTarget.orElseThrow(()->new NoSuchElementException("풀이를 찾을 수 없습니다"));

        // target을 고치려는 값으로 바꾼다
        Solution solution = solutionPatchRequest.toEntity(target);

        // solutionService로 update한다
        Solution updated = solutionService.update(solution);

        // 성공할 시 solutiuonId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI("http://localhost:8080/solutions/"+updated.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }





}
