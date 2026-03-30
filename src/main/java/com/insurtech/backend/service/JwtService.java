package com.insurtech.backend.service;

import com.insurtech.backend.domain.entity.User;
import io.jsonwebtoken.Claims;

import java.util.Set;

public interface JwtService {

    String generateAccessToken(User user);

    long getAccessTokenTtlSeconds();

    Claims validateAccessToken(String token);

    Set<String> extractRoles(Claims claims);
}
