package com.winter.app.configs.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.winter.app.board.notice.NoticeRepository;
import com.winter.app.member.MemberRepository;
import com.winter.app.member.MemberVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenManager {
// Token 생성, 관리, 검증
	
	// 1. access 토큰 유효 시간
	@Value("${jwt.accessValidTime}")
	private Long accessValidTime;
	
	// 2. refresh 토큰 유효 시간
	@Value("${jwt.refreshValidTime}")
	private Long refreshValidTime;
	
	// 3. 발급자
	@Value("${jwt.issuer}")
	private String issuer;
	 
	// 4. SecretKey
	@Value("${jwt.secretKey}")
	private String secretKey;
	
	//5. Key
	private SecretKey key;
	
	@Autowired
	private MemberRepository memberRepository; 
	
	 
	// A. Key 생성하는 메서드
	@PostConstruct
	public void init() {
		String k = Base64.getEncoder().encodeToString(this.secretKey.getBytes());
		key = Keys.hmacShaKeyFor(k.getBytes());
	}
	
	// B. Token 생성하는 메서드
	private String createToken(Authentication authentication, Long vaildTime) {
		
		return Jwts
				.builder()
				.subject(authentication.getName())
				.claim("roles", authentication.getAuthorities().toString())  // 추가 정보(필수 아님)
				.issuedAt(new Date(System.currentTimeMillis()))  // 토큰 생성 시간
				.expiration(new Date(System.currentTimeMillis() + vaildTime))  // 토큰 유효 시간
				.issuer(issuer)  // 발급자
				.signWith(key)  // key
				.compact()
				;
		
	}
	
	// C. Access Token 생성
	public String createAccessToken(Authentication authentication) {
		return this.createToken(authentication, accessValidTime);
	}
	
	// D. Refresh Token 생성
	public String createRefreshToken(Authentication authentication) {
		return this.createToken(authentication, refreshValidTime);
	}
	
	// E. Token 검증
	public Authentication verifyToken(String token) throws Exception {
		Claims claims = Jwts
				.parser()
				.verifyWith(key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				;
		
		Optional<MemberVO> result = memberRepository.findById(claims.getSubject());
		
		MemberVO memberVO = result.get();
		
		return new UsernamePasswordAuthenticationToken(memberVO, null, memberVO.getAuthorities());
		
		
		
		
		
	}
	
	
	
	
	

}
