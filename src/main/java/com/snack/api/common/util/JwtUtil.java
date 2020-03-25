package com.snack.api.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class JwtUtil {

	@Value("${jwt.token.header}")
	private String header;
	
	@Value("${jwt.token.secret}")
	private String secret;
	
	@Value("${jwt.token.expiration}")
	private Long expiration;
	
	@Value("${jwt.token.key}")
	private String key;
	
	@Value("${jwt.token.vec}")
	private String vec;
	
	public String getId(String token) {
		String id = null;
		try {

			token = decrypt(token);
			id = (String) getClaims(token).get("id");

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRoles(String token) {
		List<String> roles = null;
		try {

			token = decrypt(token);
			roles = (List<String>) getClaims(token).get("role");

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return roles;
	}
				
	public String generateToken(String id, List<String> roles) {
		String token = null;
		try {
			
			Map<String, Object> claims = new HashMap<String, Object>();
		    claims.put("id", id);
		    claims.put("role", roles);
		    claims.put("exp", generateExpirationDate());
		    token = generateToken(claims);
		    token = encrypt(token);
		    
		}
		catch (Exception e) {
			log.debug(e.getMessage());
		}
		return token;
	}
			
	public String refreshToken(String token) {
		String refreshedToken = null;
		try {
						
			token = decrypt(token);
			final Claims claims = getClaims(token);
			claims.put("exp", generateExpirationDate());
			refreshedToken = generateToken(claims);
			token = encrypt(refreshedToken);
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return token;
	}
	
	public Boolean isTokenExpired(String token) {
		boolean rslt = false;
		try {
			
			token = decrypt(token);
			Date expiration = getExpirationDate(token);
			rslt = generateCurrentDate().before(expiration);
			
		}
		catch (Exception e) {
			log.debug(e.getMessage());
		}
		return rslt; 
	}
	
	private Date getExpirationDate(String token) {
		Date expiration = null;
		try {

			expiration = new Date((Long) getClaims(token).get("exp"));
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return expiration;
	}
	
	private Claims getClaims(String token) {
		Claims claims = null;
		try {
			
			claims = Jwts.parser().setSigningKey(secret.getBytes("UTF-8")).parseClaimsJws(token).getBody();

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return claims;
	}
	
	private Date generateCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
		
	private String generateToken(Map<String, Object> claims) {
		try {
			return Jwts.builder()
					.setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, secret.getBytes("UTF-8"))
					.compact();
			
		} catch (Exception ex) {
			return null;
		}
	}
		
	private String encrypt(String token) throws Exception {
		return token;
//		try {
//			IvParameterSpec iv = new IvParameterSpec(vec.getBytes());
//			SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "AES");
//			
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
//			
//			byte[] rslt = cipher.doFinal(token.getBytes("UTF-8"));
//			
//			return Base64.encodeBase64String(rslt);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}
	
	private String decrypt(String encryted) {
		return encryted;
//		try {
//			IvParameterSpec iv = new IvParameterSpec(vec.getBytes());
//			SecretKeySpec sk = new SecretKeySpec(key.getBytes(), "AES");
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(Cipher.DECRYPT_MODE, sk, iv);
//
//			byte[] rslt = cipher.doFinal(Base64.decodeBase64(encryted));
//			
//			return new String(rslt, "UTF-8");
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}
}