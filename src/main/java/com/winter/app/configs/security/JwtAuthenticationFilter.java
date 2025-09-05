package com.winter.app.configs.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
	
	// 1. 토큰의 유효성 검증
	// 2. 토큰 발급?	
	private JwtTokenManager jwtTokenManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		// 부모 생성자 호출
		super(authenticationManager);
		
		this.jwtTokenManager = jwtTokenManager;
	}
	
	
	// 필터를 실행하는 메서드
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// 쿠키가 아니라 Header에서 꺼냄
		// Authorization : Bearer ${ACCESS_TOKEN}
		// Bearer ${ACCESS_TOKEN}
		String  header = request.getHeader("Authorization");
		
		if(header != null && header.startsWith("Bearer")) {
			header = header.substring(header.indexOf(" ") + 1);
			
			// 토큰 검증
			try {
				// 예외 발생하지 않으면 로그인 성공
				Authentication authentication = jwtTokenManager.verifyToken(header);
				// 로그인 성공 시 authentication session에 넣어주기
				SecurityContextHolder.getContext().setAuthentication(authentication);				
				
			} catch (Exception e) {
				// 검증에 실패하면 예외 발생
				e.printStackTrace();
				// access token이 만료되었지만, refresh token이 유효하다면
				// access token을 새로 발급하고, 로그인 시키고 doFilter로 통과
			}
		}
		chain.doFilter(request, response);
		
		
		
	}
	

}
