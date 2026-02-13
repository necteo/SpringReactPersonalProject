package com.sist.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sist.web.dto.LoginRequest;
import com.sist.web.dto.LoginResponse;
import com.sist.web.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberRestController {
	private final MemberService mService;	
	
	@PostMapping("/member/login")
	public ResponseEntity<LoginResponse> memberLogin(@RequestBody LoginRequest req) {
		LoginResponse res = mService.memberLogin(req);
		return ResponseEntity.ok(res);
	}
}
