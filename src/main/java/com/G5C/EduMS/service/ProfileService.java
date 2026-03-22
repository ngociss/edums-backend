package com.G5C.EduMS.service;

import com.G5C.EduMS.dto.request.ChangePasswordRequest;
import com.G5C.EduMS.dto.request.UpdateProfileRequest;
import com.G5C.EduMS.dto.response.ProfileResponse;

public interface ProfileService {

    // Lấy thông tin cá nhân
    ProfileResponse getMyProfile(String username);

    // Cập nhật thông tin cá nhân
    ProfileResponse updateMyProfile(String username, UpdateProfileRequest request);

    // Đổi mật khẩu
    void changePassword(String username, ChangePasswordRequest request);
}