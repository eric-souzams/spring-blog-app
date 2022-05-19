package io.blog.springblogapp.service.impl;

import io.blog.springblogapp.dto.UserDto;
import io.blog.springblogapp.security.SecurityConstants;
import io.blog.springblogapp.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaims(token);

            Date expirationDate = claims.getExpiration();
            LocalDateTime expirationDateTime = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            return LocalDateTime.now().isBefore(expirationDateTime);
        } catch (ExpiredJwtException exception) {
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.getSecretToken())
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String getUsername(String token) {
        Claims claims = getClaims(token);

        return claims.getSubject();
    }

    @Override
    public String generateToken(UserDto user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getSecretToken())
                .compact();
    }
}