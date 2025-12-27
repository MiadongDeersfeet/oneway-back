package com.kh.oneway.configuration.filter;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.oneway.token.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * 추상클래스 | 인터페이스
 * 단일상속     다중상속
 * 
 * 추상클래스
 * 필드의 공유가 필요할 때, 생성자가 필요할 때, 공통 구현 로직이 많을 때
 * =>
 * 다형성을 적용해야 한다.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 // TODO: Authorization 헤더에서 Bearer 토큰 추출 (담당자)
         // TODO: 토큰 검증/파싱 후 사용자 식별 (담당자)
         // TODO: SecurityContext에 인증 등록 (담당자)
         // TODO: 예외(만료/위조) 시 401/403 응답 처리 (담당자)
		
		filterChain.doFilter(request, response);
	}

}
