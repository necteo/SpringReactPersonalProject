package com.sist.web.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sist.web.entity.AuthProvider;
import com.sist.web.entity.Member;
import com.sist.web.entity.RefreshToken;
import com.sist.web.exception.CustomException;
import com.sist.web.exception.ErrorCode;
import com.sist.web.info.OAuth2UserInfo;
import com.sist.web.info.OAuth2UserInfoFactory;
import com.sist.web.repository.MemberRepository;
import com.sist.web.repository.RefreshTokenRepository;
import com.sist.web.token.JwtTokenProvider;
import com.sist.web.util.CookieUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtil cookieUtil;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${oauth2.redirect-url}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // registrationId로 provider 판별
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String registrationId = authToken.getAuthorizedClientRegistrationId();
        AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

        // provider + providerId로 회원 조회
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.of(registrationId, oAuth2User.getAttributes());
        Member member = memberRepository
                .findByProviderAndProviderId(provider, userInfo.getProviderId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        String subject = provider + "_" + userInfo.getProviderId(); // "KAKAO_12345678"

        // Access Token + Refresh Token 발급
        String accessToken  = jwtTokenProvider.createAccessToken(subject, member.getRole().getKey());
        String refreshToken = jwtTokenProvider.createRefreshToken(subject);

        // Refresh Token DB 저장 (기존 토큰 있으면 갱신)
        saveRefreshToken(member, refreshToken);

        // 사용 완료된 OAuth2 인증 상태 초기화
        clearAuthenticationAttributes(request, response);
     // 기존 addTokenCookies 메서드 대신 CookieUtil 사용
        cookieUtil.addAccessTokenCookie(response, accessToken);
        cookieUtil.addRefreshTokenCookie(response, refreshToken);

        // 이메일 여부와 무관하게 리다이렉트만
        String targetUrl = member.getEmail() == null
                ? redirectUri + "/additional-info"
                : redirectUri + "/home";
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void saveRefreshToken(Member member, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByMember(member)
                .map(existing -> existing.updateToken(refreshToken))
                .orElseGet(() -> RefreshToken.builder()
                        .member(member)
                        .token(refreshToken)
                        .build());

        refreshTokenRepository.save(token);
    }

    // OAuth2 임시 인증 정보 삭제 (세션/쿠키에 남은 state, code 등)
    private void clearAuthenticationAttributes(HttpServletRequest request,
                                               HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
