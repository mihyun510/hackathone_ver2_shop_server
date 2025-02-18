package com.kimh.spm.domain.auth.dto;

import com.kimh.spm.domain.user.User;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private User user;
    private String message;

    public AuthResponse(String token, User user, String message) {
        this.token = token;
        this.user = user;
        this.message = message;
    }
}
