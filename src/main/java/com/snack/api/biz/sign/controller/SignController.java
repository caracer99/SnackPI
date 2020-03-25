package com.snack.api.biz.sign.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snack.api.biz.sign.model.Sign;
import com.snack.api.biz.sign.service.SignService;
import com.snack.api.common.model.ApiResult;
import com.snack.api.common.util.JwtUtil;

@RestController
@RequestMapping("/api/sign")
public class SignController {

	@Autowired
	private SignService signService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping(value="/in", method=RequestMethod.POST)
	public ApiResult<?> in(HttpServletRequest req, HttpServletResponse res, @RequestBody(required=false) Sign param) throws Exception {
		
		Sign sign = signService.signIn(param);
		
		// Success
		if(null != sign) {
			
			String token = jwtUtil.generateToken(sign.getId(), sign.getRole());
			
			res.setHeader(jwtUtil.getHeader(), jwtUtil.refreshToken(token));
    		
			return new ApiResult<>()
					.success(true)
					.message("successed sign")
					.data(token);
		}
		// Fail
		else {
			return new ApiResult<>()
					.success(false)
					.message("failed sign");
		}
		
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public ApiResult<?> join(@RequestBody Sign param) throws Exception {
				
		signService.signJoin(param);
		
		return new ApiResult<>()
				.success(true);
		
	}
			
}
