package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.ChangePasswordRequest;
import com.G5C.EduMS.dto.request.UpdateProfileRequest;
import com.G5C.EduMS.dto.response.ProfileResponse;
import com.G5C.EduMS.dto.response.ResponseData;
import com.G5C.EduMS.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ResponseData<ProfileResponse>> getMyProfile(Principal principal) {
        // principal.getName() sẽ trả về username (VD: sv_2600123) từ JWT Token
        ProfileResponse result = profileService.getMyProfile(principal.getName());
        return ResponseEntity.ok(ResponseData.success("Lấy thông tin cá nhân thành công", result, HttpStatus.OK.value()));
    }

    @PutMapping("/me")
    public ResponseEntity<ResponseData<ProfileResponse>> updateMyProfile(
            Principal principal,
            @Valid @RequestBody UpdateProfileRequest request) {

        ProfileResponse result = profileService.updateMyProfile(principal.getName(), request);
        return ResponseEntity.ok(ResponseData.success("Cập nhật thông tin cá nhân thành công", result, HttpStatus.OK.value()));
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseData<Void>> changePassword(
            Principal principal,
            @Valid @RequestBody ChangePasswordRequest request) {

        profileService.changePassword(principal.getName(), request);
        return ResponseEntity.ok(ResponseData.success("Đổi mật khẩu thành công", null, HttpStatus.OK.value()));
    }
}