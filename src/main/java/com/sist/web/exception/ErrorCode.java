package com.sist.web.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TOKEN_EXPIRED("토큰이 만료되었습니다."),
    TOKEN_INVALID("유효하지 않은 토큰입니다."),
	MEMBER_NOT_FOUND("없는 회원입니다."),
	TOKEN_NOT_FOUND("없는 토큰입니다.");

    private final String message;
}
