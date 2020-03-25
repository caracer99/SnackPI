package com.snack.api.biz.sign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snack.api.biz.sign.dao.SignDao;
import com.snack.api.biz.sign.model.Sign;
import com.snack.api.biz.sign.model.SignRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignService {

	@Autowired
	private SignDao signDao;
			
	@Transactional
	public Sign signIn(Sign param) throws Exception {
		
		if(null == param || null == param.getId() || "".equals(param.getId()) || null == param.getPass() || "".equals(param.getPass())) {
			return null;
		}
				
		Sign sign = signDao.getSign(param);
		
		if(null != sign) {
									
			signDao.updSignTry(param);
						
			if(BCrypt.checkpw(param.getPass(), sign.getPass())){
				
				signDao.updSignOk(param);
				sign.setRole(signDao.getSignRole(param));
				
				return sign;
				
			}
			else {
				
				return null;
				
			}
		}

		return sign;
	}
	
	@Transactional
	public void signJoin(Sign param) throws Exception {
		
		String passwrd = param.getPass();
		
		param.setPass(BCrypt.hashpw(param.getPass(), BCrypt.gensalt()));
		
		if(BCrypt.checkpw(passwrd, param.getPass())) {
			log.info("BCrypt Success!");
			
			signDao.setSign(param);
			
			SignRole signRole = new SignRole();
			signRole.setId(param.getId());
			signRole.setRole("U");
			signDao.setSignRole(signRole);
		}
		else {
			log.info("BCrypt Error!");
		}

	}
	
}
