package com.G5C.EduMS.security;

import com.G5C.EduMS.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Account account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lấy tên Role từ DB (Ví dụ: STUDENT, ADMIN, TEACHER) và thêm tiền tố ROLE_ chuẩn Spring
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + account.getRole().getRoleName().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername(); // Đây chính là sv_123456
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return "ACTIVE".equalsIgnoreCase(account.getStatus().toString());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !account.isDeleted() && "ACTIVE".equalsIgnoreCase(account.getStatus().toString());
    }
}