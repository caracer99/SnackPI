package com.snack.api.biz.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snack.api.biz.member.model.Member;
import com.snack.api.biz.member.service.MemberService;
import com.snack.api.common.constant.Role;
import com.snack.api.common.model.ApiResult;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@Secured(Role.ADMIN)
	@RequestMapping("/list")
	public ApiResult<List<Member>> list(@RequestBody(required=false) Member param) throws Exception {
						
		return new ApiResult<List<Member>>()
					.success(true)
					.data(memberService.list(param));
		
	}
	
	@Secured(Role.USER)
	@RequestMapping("/me")
	public ApiResult<Member> one(@RequestBody(required=false) Member param) throws Exception {
		
		param = new Member();
		param.setId(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return new ApiResult<Member>()
					.success(true)
					.data(memberService.one(param));
		
	}
	
	@Secured(Role.TOKEN)
	@RequestMapping("/free")
	public ApiResult<List<Member>> list2(@RequestBody(required=false) Member param) throws Exception {
						
		return new ApiResult<List<Member>>()
					.success(true)
					.data(memberService.list(param));
		
	}
		
}
