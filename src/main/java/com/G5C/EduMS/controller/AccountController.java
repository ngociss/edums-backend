package com.G5C.EduMS.controller;

import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.dto.reponse.ResponseData;
import com.G5C.EduMS.dto.reponse.AccountResponse;
import com.G5C.EduMS.dto.reponse.PageResponse;
import com.G5C.EduMS.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // 1. Lấy danh sách (Tìm kiếm, Lọc, Phân trang)
    @GetMapping
    public ResponseEntity<ResponseData<PageResponse<AccountResponse>>> getAccounts(@ModelAttribute AccountSearchRequest searchRequest) {
        // Dùng @ModelAttribute cho GET request có truyền object qua URL Parameters
        PageResponse<AccountResponse> data = accountService.getAccounts(searchRequest);
        ResponseData<PageResponse<AccountResponse>> response = ResponseData.success("Lấy danh sách tài khoản thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 2. Lấy chi tiết Tài khoản
    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<AccountResponse>> getAccountById(@PathVariable Integer id) {
        AccountResponse data = accountService.getAccountById(id);
        ResponseData<AccountResponse> response = ResponseData.success("Lấy thông tin tài khoản thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 3. Tạo mới Tài khoản
    @PostMapping
    public ResponseEntity<ResponseData<AccountResponse>> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        AccountResponse data = accountService.createAccount(request);
        ResponseData<AccountResponse> response = ResponseData.success("Tạo tài khoản thành công", data, HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 4. Cập nhật Tài khoản cơ bản
    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<AccountResponse>> updateAccount(@PathVariable Integer id, @Valid @RequestBody AccountUpdateRequest request) {
        AccountResponse data = accountService.updateAccount(id, request);
        ResponseData<AccountResponse> response = ResponseData.success("Cập nhật tài khoản thành công", data, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 5. Cập nhật nhanh trạng thái (Khóa/Mở khóa)
    @PatchMapping("/{id}/status")
    public ResponseEntity<ResponseData<Void>> updateStatus(@PathVariable Integer id, @Valid @RequestBody AccountStatusUpdateRequest request) {
        accountService.updateAccountStatus(id, request);
        ResponseData<Void> response = ResponseData.success("Cập nhật trạng thái thành công", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    // 6. Cấp lại mật khẩu (Reset Password)
    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<ResponseData<Void>> resetPassword(@PathVariable Integer id, @Valid @RequestBody ResetPasswordRequest request) {
        accountService.resetPassword(id, request);
        ResponseData<Void> response = ResponseData.success("Cấp lại mật khẩu thành công", null, HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}