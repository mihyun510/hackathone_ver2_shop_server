package com.kimh.spm.domain.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimh.spm.domain.user.User;
import com.kimh.spm.security.auth.AuthDetailService;
import com.kimh.spm.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthDetailService authDetailService;

    // 생성자를 통해 서비스 주입
    public UserController(JwtTokenProvider jwtTokenProvider, AuthDetailService authDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authDetailService = authDetailService;
    }
    
    // 사용자 정보를 반환
    @GetMapping("/get/profile")
    public ResponseEntity<User> getUserProfile() {
    	try {
            User authenticatedUser = authDetailService.getAuthenticatedUser();
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
}
