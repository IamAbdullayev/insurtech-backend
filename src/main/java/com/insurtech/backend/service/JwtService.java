package com.insurtech.backend.service;

import com.insurtech.backend.domain.entity.User;

public interface JwtService {

  String generateAccessToken(User user);
}
