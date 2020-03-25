package com.snack.api.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.snack.api.common.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter implements Filter {

	@Autowired
	private JwtUtil jwtUtil;
		
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader(jwtUtil.getHeader());

        if(null == token || "".equals(token)) {
        	log.info("Jwt Filter : there is no token.");
        	SecurityContextHolder.clearContext();
        }
        else {
        	
        	if(jwtUtil.isTokenExpired(token)) {
            	        		            	
            	String id = jwtUtil.getId(token);
            	List<String> roles = jwtUtil.getRoles(token);
            	            	            	
            	if(null != id && null != roles) {
            		
            		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            		authorities.add(new SimpleGrantedAuthority("ROLE_T"));
            		for(String role : roles) {
                		authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                	}

            		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null, authorities);
            	
            		auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            		
            		httpResponse.setHeader(jwtUtil.getHeader(), jwtUtil.refreshToken(token));
            		
            		SecurityContextHolder.getContext().setAuthentication(auth);
            	}
            	else{
            		log.info("Jwt Filter : there is no id and autorities.");
            		SecurityContextHolder.clearContext();
            	}
            	
            }
            else {
            	log.info("Jwt Filter : Expired the token.");
            	SecurityContextHolder.clearContext();
            }
        }
                
        chain.doFilter(httpRequest, httpResponse);
	}

}
