package com.sist.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.TokenResult;
import com.sist.web.exception.CustomException;
import com.sist.web.exception.ErrorCode;
import com.sist.web.service.AuthService;
import com.sist.web.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest request,
                                        HttpServletResponse response) {
        // 쿠키에서 Refresh Token 추출
        String refreshToken = cookieUtil.resolveToken(request, "refresh_token")
                .orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));

        // 새 Access Token 발급
        TokenResult result = authService.refresh(refreshToken);
        
        // 새 Access Token 쿠키 갱신
        cookieUtil.addAccessTokenCookie(response, result.accessToken());
        cookieUtil.addRefreshTokenCookie(response, result.refreshToken());


        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                       HttpServletResponse response) {
        // 쿠키에서 Refresh Token 추출
        cookieUtil.resolveToken(request, "refresh_token")
                .ifPresent(authService::logout);  // DB에서 삭제

        // 쿠키 만료 처리
        cookieUtil.deleteTokenCookies(response);

        return ResponseEntity.noContent().build();
    }
}
