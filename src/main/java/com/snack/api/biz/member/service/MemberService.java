package com.snack.api.biz.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snack.api.biz.member.dao.MemberDao;
import com.snack.api.biz.member.model.Member;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
	public List<Member> list(Member param) throws Exception {
		return memberDao.list(param);
	}
	
	public Member one(Member param) throws Exception {
		return memberDao.one(param);
	}
	
}
