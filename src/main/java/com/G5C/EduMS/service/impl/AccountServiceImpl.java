package com.G5C.EduMS.service.impl;

import com.G5C.EduMS.dto.response.AccountResponse;
import com.G5C.EduMS.dto.response.PageResponse;
import com.G5C.EduMS.dto.request.*;
import com.G5C.EduMS.exception.ExistingResourcesException;
import com.G5C.EduMS.exception.InvalidDataException;
import com.G5C.EduMS.exception.NotFoundResourcesException;
import com.G5C.EduMS.mapper.AccountMapper;
import com.G5C.EduMS.model.Account;
import com.G5C.EduMS.model.Role;
import com.G5C.EduMS.repository.AccountRepository;
import com.G5C.EduMS.repository.RoleRepository;
import com.G5C.EduMS.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AccountResponse> getAccounts(AccountSearchRequest searchRequest) {
        // Xử lý phân trang (Spring Data JPA đánh index từ 0)
        int pageNo = searchRequest.getPage() > 0 ? searchRequest.getPage() - 1 : 0;

        // Xử lý sắp xếp (Mặc định: mới nhất lên đầu)
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (searchRequest.getSortBy() != null && !searchRequest.getSortBy().isEmpty()) {
            String[] sortParams = searchRequest.getSortBy().split(",");
            String sortBy = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
            sort = Sort.by(direction, sortBy);
        }

        Pageable pageable = PageRequest.of(pageNo, searchRequest.getSize(), sort);

        // Gọi hàm custom query với tham số prefix mới được thêm
        Page<Account> accountPage = accountRepository.searchAccounts(
                searchRequest.getKeyword(),
                searchRequest.getRoleId(),
                searchRequest.getStatus(),
                searchRequest.getPrefix(),
                pageable
        );

        List<AccountResponse> accountResponses = accountMapper.toResponseList(accountPage.getContent());

        return PageResponse.<AccountResponse>builder()
                .page(searchRequest.getPage())
                .size(searchRequest.getSize())
                .totalElements(accountPage.getTotalElements())
                .totalPages(accountPage.getTotalPages())
                .data(accountResponses)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản với ID: " + id));
        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty() || request.getPassword() == null) {
            throw new InvalidDataException("Tên đăng nhập và Mật khẩu không được để trống.");
        }

        if (accountRepository.existsByUsernameAndDeletedFalse(request.getUsername())) {
            throw new ExistingResourcesException("Tên đăng nhập đã tồn tại trong hệ thống!");
        }

        // 2. Lấy thông tin Role
        Role role = roleRepository.findByIdAndDeletedFalse(request.getRoleId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Vai trò (Role) hợp lệ!"));

        // 3. Khởi tạo và mã hóa mật khẩu
        Account newAccount = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .avatarUrl(request.getAvatarUrl())
                // status mặc định là ACTIVE
                .build();

        Account savedAccount = accountRepository.save(newAccount);
        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Integer id, AccountUpdateRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new InvalidDataException("Tên đăng nhập không được để trống.");
        }

        Account existingAccount = accountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản để cập nhật!"));

        // Kiểm tra username mới có bị trùng với người khác không
        if (!existingAccount.getUsername().equals(request.getUsername()) &&
                accountRepository.existsByUsernameAndDeletedFalse(request.getUsername())) {
            throw new ExistingResourcesException("Tên đăng nhập mới đã bị trùng với người khác!");
        }

        Role newRole = roleRepository.findByIdAndDeletedFalse(request.getRoleId())
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy Vai trò (Role) mới hợp lệ!"));

        existingAccount.setUsername(request.getUsername());
        existingAccount.setRole(newRole);
        existingAccount.setAvatarUrl(request.getAvatarUrl());

        Account savedAccount = accountRepository.save(existingAccount);
        return accountMapper.toResponse(savedAccount);
    }

    @Override
    @Transactional
    public void updateAccountStatus(Integer id, AccountStatusUpdateRequest request) {
        if (request.getStatus() == null) {
            throw new InvalidDataException("Trạng thái không được để trống!");
        }

        Account account = accountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản!"));

        account.setStatus(request.getStatus());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void resetPassword(Integer id, ResetPasswordRequest request) {
        if (request.getNewPassword() == null || request.getConfirmPassword() == null) {
            throw new InvalidDataException("Vui lòng nhập đầy đủ mật khẩu mới và xác nhận!");
        }
// Logic kiểm tra 2 mật khẩu khớp nhau
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidDataException("Mật khẩu xác nhận không khớp!");
        }

        Account account = accountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundResourcesException("Không tìm thấy tài khoản!"));

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
    }
}
