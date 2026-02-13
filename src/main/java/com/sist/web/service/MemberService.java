package com.sist.web.service;

import com.sist.web.dto.LoginRequest;
import com.sist.web.dto.LoginResponse;

public interface MemberService {
	public LoginResponse memberLogin(LoginRequest req);
}
