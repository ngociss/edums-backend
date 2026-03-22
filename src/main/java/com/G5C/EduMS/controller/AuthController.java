package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.LoginRequest;
import com.G5C.EduMS.dto.response.AuthResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.security.CustomUserDetails;
import com.G5C.EduMS.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        // 1. Uỷ quyền cho Spring Security kiểm tra Username & Password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. Nếu đăng nhập đúng, lấy thông tin User ra
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // 3. Đóng dấu (Generate) JWT Token
        String jwtToken = jwtService.generateToken(userDetails);

        // 4. Trả về Frontend
        AuthResponse authResponse = AuthResponse.builder()
                .token(jwtToken)
                .accountId(userDetails.getAccount().getId())
                .username(userDetails.getUsername())
                .role(userDetails.getAccount().getRole().getRoleName())
                .build();

        return ResponseEntity.ok(ResponseData.success("Đăng nhập thành công", authResponse, HttpStatus.OK.value()));
    }
}