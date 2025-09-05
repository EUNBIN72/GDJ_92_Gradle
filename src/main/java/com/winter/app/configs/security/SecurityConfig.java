package com.winter.app.configs.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtTokenManager jwtTokenManager;
	
	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Autowired
	private AddLogoutSuccessHandler addLogoutSuccessHandler;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.cors(cors -> cors.configurationSource(this.corsConfiguration()))  // CORS 보안 비활성화
			.csrf(csrf -> csrf.disable())  // CSRF 토큰 비활성화
			
			// 1. 권한 설정(인가)
			.authorizeHttpRequests(
					auth -> {
						auth
							.requestMatchers("/api/notice/add").hasRole("ADMIN")
							.requestMatchers("/api/notice").authenticated()
							.anyRequest().permitAll()
							;
				
			})
		
			// 2. Form Login
			.formLogin(formLogin -> formLogin.disable())
			
			// 3. Logout 설정
			.logout(logout -> {
				logout
					.logoutUrl("/api/member/logout")
					.invalidateHttpSession(true)
					.deleteCookies("access_token", "refresh_token")
					.logoutSuccessHandler(addLogoutSuccessHandler)
					;
					
			})
			
			// 4. Session
			.sessionManagement(session -> {
				session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // 세션을 유지하지 않겠다
			})
			
			// 5. HttpBasic
			.httpBasic(http -> http.disable())
			
			// 6. Token에 관련된 필터를 등록
			.addFilter(new JwtAuthenticationFilter(this.authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			.addFilter(new JwtLoginFilter(this.authenticationConfiguration.getAuthenticationManager(), jwtTokenManager))
			;
		
		
		return httpSecurity.build();
		
	}
	
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of( "*"));
		
		// configuration.setAllowCredentials(true) 를 사용하면
		// setAllowedOrigins를 사용이 안됨
		// setAllowedOriginPatterns를 써야 됨
		// *(와일드카드) 사용 안됨
//		configuration.setAllowedOriginPatterns(List.of("http://localhost:*"));
//		configuration.setAllowCredentials(true);
		
		// 메서드에서 *은 사용 안됨
		configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "PUT", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		
		configuration.setExposedHeaders(List.of("accesstoken", "Accesstoken"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
			source.registerCorsConfiguration("/**", configuration);
			
			return source;
		
	}
	
	
}
