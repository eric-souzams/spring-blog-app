package io.blog.springblogapp.service;

import io.blog.springblogapp.dto.UserDto;
import io.jsonwebtoken.Claims;

public interface JwtService {

    boolean isValidToken(String token);

    Claims getClaims(String token);

    String getUsername(String token);

    String generateToken(UserDto user);

}