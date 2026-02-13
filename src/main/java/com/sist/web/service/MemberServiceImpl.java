package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.dto.LoginRequest;
import com.sist.web.dto.LoginResponse;
import com.sist.web.entity.Member;
import com.sist.web.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository mRepo;

	@Override
	public LoginResponse memberLogin(LoginRequest req) {
		LoginResponse res = new LoginResponse();
		int count = mRepo.memberIdCount(req.id());
		if (count == 0) {
			res.setMsg("NOID");
		} else {
			Member m = mRepo.memberInfoData(req.id()).orElseThrow();
			if (req.pwd().equals(m.getPwd())) {
				res.setId(req.id());
				res.setName(m.getName());
				res.setMsg("OK");
			} else {
				res.setMsg("NOPWD");
			}
		}
		return res;
	}
}
