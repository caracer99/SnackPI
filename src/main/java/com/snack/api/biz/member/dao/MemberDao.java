package com.snack.api.biz.member.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.snack.api.biz.member.model.Member;

@Mapper
public interface MemberDao {

	public List<Member> list(Member param) throws Exception;
	
	public Member one(Member param) throws Exception;
	
}