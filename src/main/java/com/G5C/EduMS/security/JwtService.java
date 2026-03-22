package com.G5C.EduMS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Chuỗi bí mật ký Token (Phải dài tối thiểu 256-bit). Trong thực tế nên để ở application.properties
    @Value("${application.security.jwt.secret-key:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secretKey;

    @Value("${application.security.jwt.expiration:86400000}") // Mặc định 1 ngày (mili giây)
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims) // Dùng claims thay vì setClaims
                .subject(userDetails.getUsername()) // Dùng subject thay vì setSubject
                .issuedAt(new Date(System.currentTimeMillis())) // Dùng issuedAt
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Dùng expiration
                .signWith(getSignInKey()) // Bản mới không cần truyền SignatureAlgorithm.HS256 nữa
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey()) // Dùng verifyWith thay vì setSigningKey
                .build()
                .parseSignedClaims(token) // Dùng parseSignedClaims thay vì parseClaimsJws
                .getPayload(); // Dùng getPayload thay vì getBody
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}