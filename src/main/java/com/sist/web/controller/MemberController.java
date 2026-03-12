package com.sist.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.entity.AuthProvider;
import com.sist.web.entity.Member;
import com.sist.web.exception.CustomException;
import com.sist.web.exception.ErrorCode;
import com.sist.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        // JWT subject = "PROVIDER_providerId"
        String subject = userDetails.getUsername();
        String[] parts = subject.split("_", 2);

        Member member = memberRepository.findByProviderAndProviderId(
                AuthProvider.valueOf(parts[0]),
                parts[1]
        ).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return ResponseEntity.ok(new MemberResponse(member.getName(), member.getEmail(), member.getPicture()));
    }

    public record MemberResponse(String name, String email, String picture) {}
}
