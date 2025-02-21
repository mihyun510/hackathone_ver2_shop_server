package com.kimh.spm.security.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.kimh.spm.domain.user.User;
import com.kimh.spm.domain.user.UserService;
import com.kimh.spm.security.auth.dto.AuthRequest;
import com.kimh.spm.security.auth.dto.AuthResponse;
import com.kimh.spm.security.jwt.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthDetailService authDetailService;
    private final JwtTokenProvider jwtAuthenticationService;
    private final UserService userService;
    
    public AuthController(AuthenticationManager authenticationManager, AuthDetailService authDetailService
    					, JwtTokenProvider jwtAuthenticationService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.authDetailService = authDetailService;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.userService = userService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            return ResponseEntity.ok("hello world");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsId(), authRequest.getUsPw())
            );

            UserDetails userDetails = authDetailService.loadUserByUsername(authRequest.getUsId());
            User user = ((AuthDetail) userDetails).getUser(); // 원래 User 객체 가져오기
            String token = jwtAuthenticationService.generateToken(user.getUsNm(), user.getUsRole());

            return ResponseEntity.ok(new AuthResponse(token, user, "Success"));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new AuthResponse(null, null,"Invalid credentials"));
        }
    }
    
    @PostMapping("/save/user")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        try {
            // 사용자 생성
            User createdUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
        }
    }
    
    // 비밀번호 재설정 엔드포인트
    @PutMapping("/modify/user/password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
    	
    	String usId = payload.get("usId");
        try {
            // 사용자 생성
            int result = userService.resetPassword(usId);
            if(result > 0) return ResponseEntity.ok("Password reset successfully");
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
    
    
}