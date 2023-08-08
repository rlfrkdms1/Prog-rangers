package com.prograngers.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.prograngers.backend.AuthConstant.VALID_TIME_REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        AuthResult authResult = memberService.signUp(signUpRequest);
        ResponseCookie cookie = ResponseCookie.from("refreshToken", authResult.getRefreshToken())
                .httpOnly(true)
                .maxAge(VALID_TIME_REFRESH_TOKEN)
                .secure(true)
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResult.getAccessToken());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        AuthResult authResult = memberService.login(loginRequest);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", authResult.getRefreshToken())
                .httpOnly(true)
                .maxAge(VALID_TIME_REFRESH_TOKEN)
                .secure(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResult.getAccessToken());
    }
}
