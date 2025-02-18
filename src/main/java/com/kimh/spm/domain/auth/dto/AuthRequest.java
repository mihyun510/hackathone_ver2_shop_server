package com.kimh.spm.domain.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String usId;
    private String usPw;
}
