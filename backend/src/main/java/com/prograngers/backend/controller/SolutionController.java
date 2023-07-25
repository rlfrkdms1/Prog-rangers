package com.prograngers.backend.controller;

import com.prograngers.backend.dto.ErrorResponse;
import com.prograngers.backend.dto.SolutionRequest;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.service.SolutionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    @PostMapping("/new-form")
    public ResponseEntity<?> newForm(@RequestBody @Validated SolutionRequest solutionRequest, BindingResult bindingResult,
                                     HttpServletResponse response) throws IOException, URISyntaxException {

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ErrorResponse("백준, 프로그래머스에 대한 문제의 풀이만 작성할 수 있습니다"));
        }

        // 리포지토리 활용해 저장
        Solution solution = Solution.toEntity(solutionRequest);
        solutionService.save(solution);

        // 성공할 시 problemId에 해당하는 URI로 리다이렉트, 상태코드 302
        URI redirectUri = new URI("http://localhost:8080/solutions/"); //ㅔ개ㅠ
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);

    }

}
