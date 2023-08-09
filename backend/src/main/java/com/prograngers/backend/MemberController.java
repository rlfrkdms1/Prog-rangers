package com.prograngers.backend;

import com.prograngers.backend.exception.NotExistTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final String REFRESH_TOKEN = "refreshToken";

    @PostMapping("/sign-up")
    public ResponseEntity<String> singUp(@RequestBody SignUpRequest signUpRequest) {
        AuthResult authResult = memberService.signUp(signUpRequest);
        ResponseCookie cookie = createCookieWithRefreshToken(authResult.getRefreshToken(), authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResult.getAccessToken());
    }

    private static ResponseCookie createCookieWithRefreshToken(String refreshToken, Long expiredAt) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .maxAge(expiredAt)
                .secure(true)
                .path("/")
                .build();
        return cookie;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        AuthResult authResult = memberService.login(loginRequest);
        ResponseCookie cookie = createCookieWithRefreshToken(authResult.getRefreshToken(), authResult.getRefreshTokenExpiredAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResult.getAccessToken());
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(@CookieValue(name = REFRESH_TOKEN) String refreshToken) {
        if (refreshToken == null) {
            throw new NotExistTokenException();
        }
        AuthResult authResult = memberService.reissue(refreshToken);
        ResponseCookie cookie = createCookieWithRefreshToken(authResult.getRefreshToken(), authResult.getRefreshTokenExpiredAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authResult.getAccessToken());
    }

    @GetMapping("/test")
    @Login
    public String test(@LoggedInMember Long memberId){
        return String.valueOf(memberId);
    }
}
