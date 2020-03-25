package com.snack.api.biz.sign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.snack.api.biz.sign.model.Sign;
import com.snack.api.biz.sign.model.SignRole;

@Mapper
public interface SignDao {

	public Sign getSign(Sign sign) throws Exception;
	
	public List<String> getSignRole(Sign sign) throws Exception;
	
	public void setSign(Sign sign) throws Exception;
	
	public void setSignRole(SignRole signRole) throws Exception;
	
	public void updSignTry(Sign sign) throws Exception;
	
	public void updSignOk(Sign sign) throws Exception;
				
}
