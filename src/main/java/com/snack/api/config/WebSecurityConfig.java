package com.snack.api.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.snack.api.common.filter.JwtFilter;
import com.snack.api.common.util.JwtUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, proxyTargetClass=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public JwtUtil jwtUtil;
	
	@Autowired
	public JwtFilter jwtFilter;
		
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
			.antMatchers("/css/**")
			.antMatchers("/script/**")
			.antMatchers("/image/**")
			.antMatchers("/fonts/**")
			.antMatchers("/lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors();
		
		http.csrf().disable();
		
		http.exceptionHandling().authenticationEntryPoint((req, rsp, e)->rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
					
		http.formLogin().disable();
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
				
	}	

}
