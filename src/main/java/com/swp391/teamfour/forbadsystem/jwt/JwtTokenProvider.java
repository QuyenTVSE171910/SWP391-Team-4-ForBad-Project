package com.swp391.teamfour.forbadsystem.jwt;

import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.app.jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.app.jwtExpirationsMs}")
    private int jwtExpirationMs;

    @Autowired
    private UserRepository userRepository;

    public String generateJwtToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtExpirationMs);
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String getUserNameFromJwtToken(String token) {
        Claims claims = getClaimsFromJwtToken(token);

        if (claims != null && !isTokenExpired(claims)) {
            if (!userRepository.existsByEmail(claims.getSubject())) {
                throw new RuntimeException("Invalid JWT Token !");
            }
            return claims.getSubject();
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
