package com.prograngers.backend.controller.auth;

import static com.prograngers.backend.controller.auth.RefreshCookieProvider.REFRESH_TOKEN;

import com.prograngers.backend.dto.auth.request.LoginRequest;
import com.prograngers.backend.dto.auth.request.SignUpRequest;
import com.prograngers.backend.dto.auth.response.LoginResponse;
import com.prograngers.backend.dto.auth.result.AuthResult;
import com.prograngers.backend.exception.unauthorization.NotExistRefreshTokenException;
import com.prograngers.backend.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;
    private final RefreshCookieProvider refreshCookieProvider;

    @GetMapping("/auth")
    public ResponseEntity<Void> checkNicknameDuplication(@RequestParam String nickname) {
        authService.validAlreadyExistNickname(nickname);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<LoginResponse> singUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        AuthResult authResult = authService.signUp(signUpRequest);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResult authResult = authService.login(loginRequest);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code) {
        log.info("kakao's authorization code is {}", code);
        AuthResult authResult = authService.kakaoLogin(code);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/login/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestParam String code) {
        AuthResult authResult = authService.googleLogin(code);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/login/naver")
    public ResponseEntity<LoginResponse> naverLogin(@RequestParam String code, @RequestParam String state) {
        AuthResult authResult = authService.naverLogin(code, state);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/reissue")
    public ResponseEntity<LoginResponse> reissue(@CookieValue(name = REFRESH_TOKEN) String refreshToken) {
        validRefreshToken(refreshToken);
        AuthResult authResult = authService.reissue(refreshToken);
        ResponseCookie cookie = refreshCookieProvider.createCookieWithRefreshToken(authResult.getRefreshToken(),
                authResult.getRefreshTokenExpiredAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(LoginResponse.from(authResult));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue(name = REFRESH_TOKEN) String refreshToken) {
        validRefreshToken(refreshToken);
        ResponseCookie logoutCookie = refreshCookieProvider.createLogoutCookie();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                .build();
    }

    private void validRefreshToken(String refreshToken) {
        if (refreshToken == null) {
            throw new NotExistRefreshTokenException();
        }
    }
}
