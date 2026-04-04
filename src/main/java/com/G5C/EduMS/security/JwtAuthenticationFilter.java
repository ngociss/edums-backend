package com.G5C.EduMS.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Nếu API không gửi kèm Token hoặc Token không bắt đầu bằng Bearer thì cho đi tiếp (Sẽ bị chặn ở SecurityConfig nếu API đó bắt buộc)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Cắt lấy đoạn mã đằng sau chữ "Bearer "
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            log.warn("Cảnh báo: Token đã hết hạn.");
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.warn("Cảnh báo: Token không hợp lệ hoặc bị chỉnh sửa.");
        } catch (Exception e) {
            log.error("Lỗi khi xử lý JWT Token: {}", e.getMessage());
        }

        // Nếu giải mã ra có Username nhưng SecurityContext chưa ghi nhận người này đang đăng nhập
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            Integer tokenAccountId = jwtService.extractClaim(jwt, claims -> claims.get("accountId", Integer.class));
            if (tokenAccountId != null && tokenAccountId.equals(customUserDetails.getAccount().getId())) {
                // Kiểm tra Token có hợp lệ với User này không
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Ép Spring Security công nhận người này đã đăng nhập hợp lệ
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } else {
                log.warn("Cảnh báo bảo mật: Token chứa username {} nhưng accountId {} không khớp với DB (có thể là token của tài khoản đã bị xóa mềm).", username, tokenAccountId);
            }
        }
        filterChain.doFilter(request, response);
    }
}