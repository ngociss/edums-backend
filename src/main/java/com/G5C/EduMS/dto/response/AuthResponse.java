package com.G5C.EduMS.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Integer accountId;
    private String username;
    private String role;
}