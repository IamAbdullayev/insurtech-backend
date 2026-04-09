package com.insurtech.backend.service.impl;

import com.insurtech.backend.config.AuthProperties;
import com.insurtech.backend.domain.entity.User;
import com.insurtech.backend.service.JwtService;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtEncoder jwtEncoder;
  private final AuthProperties authProperties;

  public String generateAccessToken(User user) {
    if (Objects.isNull(user)) throw new IllegalArgumentException("User is null!");

    Instant now = Instant.now();

    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .subject(user.getId().toString())
            .issuer(authProperties.jwt().issuer())
            .audience(List.of(authProperties.jwt().audience()))
            .issuedAt(now)
            .expiresAt(now.plusSeconds(authProperties.jwt().accessTokenTtlSeconds()))
            .claim("email", user.getEmail())
            .claim("roles", user.getRoles().stream().toList())
            .build();

    JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256).build();
    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
  }

  // For manual validation tokens (NOT currently in use) - used JJWT library
  //  public Claims validateAccessToken(String token) {
  //    try {
  //      return Jwts.parser()
  //          .verifyWith(pubicKey)
  //          .requireIssuer(authProperties.jwt().issuer())
  //          .requireAudience(authProperties.jwt().audience())
  //          .build()
  //          .parseSignedClaims(token)
  //          .getPayload();
  //
  //    } catch (ExpiredJwtException e) {
  //      log.warn("JWT expired: {}", e.getMessage());
  //      throw new AuthException(ErrorCode.TOKEN_EXPIRED, "Access token has expired");
  //    } catch (SignatureException e) {
  //      log.warn("JWT invalid signature: {}", e.getMessage());
  //      throw new AuthException(ErrorCode.TOKEN_INVALID, "Invalid token signature");
  //    } catch (MalformedJwtException | IllegalArgumentException e) {
  //      log.warn("JWT malformed: {}", e.getMessage());
  //      throw new AuthException(ErrorCode.TOKEN_INVALID, "Malformed token");
  //    } catch (JwtException e) {
  //      log.warn("JWT validation failed: {}", e.getMessage());
  //      throw new AuthException(ErrorCode.TOKEN_INVALID, "Token validation failed");
  //    }
  //  }
}
