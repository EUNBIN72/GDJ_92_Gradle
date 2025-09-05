package com.winter.app.configs.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	private JwtTokenManager jwtTokenManager;
	
	
	public JwtLoginFilter(AuthenticationManager authenticationManager, JwtTokenManager jwtTokenManager) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenManager =jwtTokenManager;
		
		this.setFilterProcessesUrl("/api/member/login");
		
	}
	
	// 로그인 처리
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("로그인 시도");
		
		// 1. uesername, password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authenticationToken);
		
	}
	
	
	// 로그인이 성공 했을 때 실행 메서드
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		// 사용자의 정보로 Token 생성
		String accesstoken = jwtTokenManager.createAccessToken(authResult);
		String refreshtoken = jwtTokenManager.createRefreshToken(authResult);
		
		// 개발시 포트번호가 다르기 때문에 쿠키가 저장되지 않음
		// Boot에 같이 빌드해서 배포하면 쿠키가 저장됨
		response.setHeader("accesstoken", accesstoken);
				
		
	}
	
	
	// 로그인이 실패 했을 때 실행 메서드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		response.setCharacterEncoding("UTF-8");
		
		response.getWriter().print(failed.getMessage());
		
		
		
	}
	
	
	
	
	

}
