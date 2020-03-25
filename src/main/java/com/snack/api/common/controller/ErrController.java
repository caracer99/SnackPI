package com.snack.api.common.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snack.api.common.model.ApiResult;

@RestController
public class ErrController implements ErrorController {

	@RequestMapping("/error")
	public ApiResult<?> handelError(HttpServletRequest req){
		
		Object stat = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Object msg = req.getAttribute(RequestDispatcher.ERROR_MESSAGE);
				
		if(null != stat) {
			return new ApiResult<>()
					.success(false)
					.status(Integer.valueOf(stat.toString()))
					.message(msg.toString());
		}
		else {
			return new ApiResult<>()
					.success(false)
					.status(500);
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
}
