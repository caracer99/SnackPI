package com.snack.api.common.model;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiResult<T> {

	private boolean success = true;
	private int status = HttpStatus.OK.value();
	private String message = "";
	private T data;
	
	public ApiResult<T> success(boolean success){
		this.success = success;		
		return this;
	}
	
	public ApiResult<T> status(int status){
		this.status = status;
		return this;
	}
	
	public ApiResult<T> message(String message){
		this.message = message;
		return this;
	}
	
	public ApiResult<T> data(T data){
		this.data = data;		
		return this;
	}
	
}
