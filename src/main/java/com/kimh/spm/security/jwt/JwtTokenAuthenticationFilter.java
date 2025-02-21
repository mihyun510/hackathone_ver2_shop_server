package com.kimh.spm.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kimh.spm.security.auth.AuthDetailService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthDetailService authDetailService;
    private final JwtTokenProvider jwtAuthenticationService;

    public JwtTokenAuthenticationFilter(AuthDetailService authDetailService, JwtTokenProvider jwtAuthenticationService) {
        this.authDetailService = authDetailService;
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	 // 요청 URI 가져오기
        String requestURI = request.getRequestURI();

        // 인증 요청에 대해서만 필터를 패스
        if (requestURI.startsWith("/auth/**")) {
            filterChain.doFilter(request, response);
            return;
        }
        
    	//헤더 인증값 가져오기 
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String usNm;

        //헤더에 token값이 없으면 패스한다.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //jwt 추출한다. 	
        jwt = authHeader.substring(7);  // "Bearer " 이후의 JWT 추출
        usNm = jwtAuthenticationService.extractUserId(jwt); //username 꺼내오

        //username 꺼내와서 jwt와 user token비
        if (usNm != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = authDetailService.loadUserByUsername(usNm);
            if (jwtAuthenticationService.validateToken(jwt, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken); //인증객체 생
            }
        }
        filterChain.doFilter(request, response);
    }


}

